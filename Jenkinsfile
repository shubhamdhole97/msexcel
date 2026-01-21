pipeline {
    agent any

    stages {
        stage('Clone ReposItory') {
            steps {
                git branch: 'dev-test', url: 'https://github.com/shubhamdhole97/msexcel.git'
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
