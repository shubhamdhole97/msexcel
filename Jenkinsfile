node {
    stage('Clone') {
        steps {
           checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'shubhamdhole97jenkins', url: 'git@github.com:shubhamdhole97/msexcel.git']])
        }
    }
    stage('Toooooooooool Checks') {
        steps {
            bat 'java -version'
            bat 'javac -version'
            bat 'mvn -version'
            echo "All tools are working fine"
        }
    }
    stage('Build') {
        steps {
            bat 'mvn clean'
            bat 'mvn package'
            echo "Building Done"
        }
    }
    stage('Test') {
        steps {
            echo "Testing"
        }
    }
    stage('Deploy') {
        steps {
            echo "Deploying"
        }
    }
}
