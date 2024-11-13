node {
    stage ('clone') {
         git branch: 'main', credentialsId: 'shubhamdhole97jenkins', url: 'git@github.com:shubhamdhole97/msexcel.git'
    }
    stage ('Tool Cchecks') {
        bat 'java -version'
        bat 'javac -version'
        bat 'mvn -version'
        echo "All are working fine"
    }
    stage ('Buaild') {
        bat 'mvn clean'
        bat 'mvn package'
        echo "Building Done"
    }
    stage ('Test') {
        echo "Testing"
    }
    stage ('Deplsoy') {
        echo "Deploying"
    }
