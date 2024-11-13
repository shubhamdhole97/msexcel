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
                bat 'java -version'
                bat 'javac -version'
                bat 'mvn -version'
                echo "All tools are working"
            }
        }
        stage('02.Clean') {
            steps {
                echo "Cleaning"
                bat 'mvn clean'
            }
        }
        stage('03.Package') {
            steps {
                echo "Packaging"
                bat 'mvn package'
            }
        }
    }
}
