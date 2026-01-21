pipeline {
    agent any

    environment {
        PROJECT_NAME = 'excel'
        DOCKER_REGISTRY = 'hub.docker.com'
        DOCKER_USERNAME = 'shubhamdhole97'
        GIT_TAG = ''
        BUILD_TAG = ''
        DOCKER_BUILDKIT = "0"
    }

    parameters {
        choice(
            name: 'ENVIRONMENT',
            choices: ['dev', 'qa'],
            description: 'Choose the environment to deploy to'
        )
    }

    stages {

        stage('Clone ReposItory') {
            steps {
                git branch: 'dev-test', url: 'https://github.com/shubhamdhole97/msexcel.git'
            }
        }

        stage("Generate Build Tag") {
            steps {
                script {
                    env.GIT_TAG = sh(script: 'git describe --tags --abbrev=0 2>/dev/null || echo "v0.0.0"', returnStdout: true).trim()
                    env.BUILD_TAG = "${env.GIT_TAG}_${env.BUILD_NUMBER}"
                    echo "Generated Docker Tag: ${env.BUILD_TAG}"
                }
            }
        }

        stage('Environment Variables') {
            steps {
                script {
                    // envar.groovy must be in your repo (workspace) for this to work
                    load "envar.groovy"
                }
            }
        }

        stage('Maven Clean') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('Maven Build') {
            steps {
                sh 'mvn package'
            }
        }

        stage('04.Deploy to Nexus') {
            steps {
                echo "Deploy to Nexus"
                sh '''
                    curl -v -u admin:admin \
                    --upload-file target/msexcel-0.0.1-SNAPSHOT.jar \
                    http://136.113.158.106:8081/repository/bham/com/msoffice/msexcel/0.0.1/msexcel-0.0.1-SNAPSHOT.jar
                '''
            }
        }

        stage('05.Verify Deployment') {
            steps {
                script {
                    def response = sh(script: '''
                        curl -s -o /dev/null -w "%{http_code}" \
                        http://136.113.158.106:8081/repository/bham/com/msoffice/msexcel/0.0.1/msexcel-0.0.1-SNAPSHOT.jar
                    ''', returnStdout: true).trim()

                    if (response == '200') {
                        echo 'Deployment verified successfully. Artifact exists in Nexus.'
                    } else {
                        error 'Deployment verification failed. Artifact not found in Nexus.'
                    }
                }
            }
        }
    }
}
