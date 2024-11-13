node {
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
