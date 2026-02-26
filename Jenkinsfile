pipeline {

    agent any

    environment {

        PROJECT_NAME = 'msexcel'

        DOCKER_REGISTRY = 'shubhamdhole97'

        GIT_TAG = ''

        BUILD_TAG = ''

        DOCKER_BUILDKIT = "0"

    }

    parameters {

        choice(

            name: 'ENVIRONMENT',

            choices: ['dev', 'qa', 'uat', 'sit'],

            description: 'Choose the environment to deploy to'

        )

    }

    stages {

        stage('Clone ReposItory') {
            steps {
                git branch: 'main', url: 'git@github.com:shubhamdhole97/msexcel.git'
            }
        }

        stage("Generate Build Tag") {

            steps {

                script {

                    env.GIT_TAG = sh(script: "git describe --tags --abbrev=0 2>/dev/null || echo v1.0.0", returnStdout: true).trim()

                    env.BUILD_TAG = "${env.GIT_TAG}_${env.BUILD_NUMBER}"

                    echo "Generated Docker Tag: ${env.BUILD_TAG}"

                }

            }
        }

        stage('Environment Variables') {

            steps {

                script {

                    load "$JENKINS_HOME/workspace/$JOB_NAME/envar.groovy"

                }

            }

        }

        stage('Initialization') {

            steps {

                sh '''

                    echo "PATH = ${PATH}"
                    java -version
                    mvn -version

                '''

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

        stage('SonarQube Security') {

            steps {

                script {

                    def scannerHome = tool 'SonarQubeScanner'

                    withCredentials([usernamePassword(

                        credentialsId: 'sonarqube-admin-creds',

                        usernameVariable: 'SONAR_USER',

                        passwordVariable: 'SONAR_PASS'

                    )]) {

                        withSonarQubeEnv('sonarqube-security') {

                            sh """
                                mvn clean verify sonar:sonar \
                                -Dspring.profiles.active=${params.ENVIRONMENT} \
                                -Dsonar.login=$SONAR_USER \
                                -Dsonar.password=$SONAR_PASS
                            """

                        }

                    }

                }

            }

        }

    }

}
