pipeline {
    agent any 

    stages {
        stage('01.Clone Repo') {
            steps {
                echo "Git Cloning"
                git branch: 'main', credentialsId: 'shubhamdhole97jenkins', url: 'git@github.com:shubhamdhole97/msexcel.git'
            }
        }
        stage('Tools Check') {
            steps {
                sh 'java -version'
                sh 'javac -version'
                sh 'mvn -version'
                echo "All tools are working"
            }
        }
        stage('02.Clean') {
            steps {
                echo "Cleaning"
                sh 'mvn clean'
            }
        }
        stage('03.Package') {
            steps {
                echo "Packaging"
                sh 'mvn package'
            }
        }
    }
}
