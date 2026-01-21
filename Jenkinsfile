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
            choices: ['dev', 'qa', 'uat', 'sit'],
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
                    GIT_TAG = sh(script: 'git describe --tags --abbrev=0 2>/dev/null || echo "v0.0.0"', returnStdout: true).trim()
                    BUILD_TAG = "${GIT_TAG}_${BUILD_NUMBER}"
                    echo "Generated Docker Tag: ${BUILD_TAG}"
                }
            }
        }

        stage('Release Number') {
            steps {
                script {
                    def build_no = "${GIT_TAG}_${env.BUILD_NUMBER}"
                    echo "Build Number: ${build_no}"
                    writeFile(file: 'src/main/resources/BuildNumber', text: build_no)
                }
            }
        }

        stage('Environment Variables') {
            steps {
                script {
                    load "$JENKINS_HOME/workspace/$Job_Name/envar.groovy"
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

        stage('Build Images') {
            steps {
                sh 'mvn clean package'
                script {
                    docker.withRegistry(env.DOCKER_REGISTRY) {
                        def imageTag = getImageTag(params.ENVIRONMENT)
                        def imageName = "${env.DOCKER_REGISTRY}/${PROJECT_NAME}.jar:${imageTag}"
                        def dockerImage = docker.build(imageName)
                    }
                }
            }
        }

        // ✅ TRIVY (uses workspace cache, and calls "trivy" from PATH)
        stage('Trivy Scan Image') {
            steps {
                script {
                    def imageTag = getImageTag(params.ENVIRONMENT)
                    def imageName = "${env.DOCKER_REGISTRY}/${PROJECT_NAME}.jar:${imageTag}"

                    sh """
                      set -e
                      export TRIVY_CACHE_DIR=\$WORKSPACE/.trivy-cache
                      mkdir -p "\$TRIVY_CACHE_DIR"

                      echo "Trivy version:"
                      trivy --version

                      echo "Downloading Trivy DB..."
                      trivy image --download-db-only --no-progress

                      echo "Scanning image: ${imageName}"
                      docker image inspect ${imageName} >/dev/null

                      trivy image \
                        --no-progress \
                        --severity HIGH,CRITICAL \
                        --ignore-unfixed \
                        ${imageName} | tee trivy-report.txt
                    """

                    archiveArtifacts artifacts: 'trivy-report.txt', allowEmptyArchive: true
                }
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

// Helper function used by Build Images stage
def getImageTag(envName) {
    // Example tag format: dev-v0.0.0_15
    return "${envName}-${BUILD_TAG}"
}
