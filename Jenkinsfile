node {
    stage ('clone') {
         git branch: 'main', credentialsId: 'shubhamdhole97jenkins', url: 'git@github.com:shubhamdhole97/msexcel.git'
    }
    stage ('Tool Checks') {
        bat 'java -version'
        bat 'javac -version'
        bat 'mvn -version'
        echo "All are workinf fine"
    }
    stage ('Build') {
        bat 'mvn clean'
        bat 'mvn package'
        echo "Building Done"
    }
    stage ('Test') {
        echo "Testing"
    }
    stage ('Deploy') {
        echo "Deploying"
    }
