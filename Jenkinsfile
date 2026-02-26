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

                    GIT_TAG = sh(script: "git describe --tags --abbrev=0", returnStdout: true).trim()

                    BUILD_TAG = "${GIT_TAG}_${BUILD_NUMBER}"

                    echo "Generated Docker Tag: ${BUILD_TAG}"

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

        stage('Initialization') {

            steps {

                sh '''

                    echo "PATH = ${PATH}"

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

    }

