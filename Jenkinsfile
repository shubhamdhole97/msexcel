pipeline {
    agent any 

    stages {
        stage('01.Clone Repo') {
            steps {
                echo "Git Cloning"
                // git branch: 'main', credentialsId: 'github-mujahed-account', url: 'git@github.com:NubeEra-MCO/Java-CBCICD.git'
            }
        }
        stage('02.Clean') {
            steps {
                echo "Cleaning"
                // bat 'mvn clean'
            }
        }
        stage('03.Package') {
            steps {
                echo "Packaging"
                // bat 'mvn package'
            }
        }
        stage('04.Deploy to Nexus'){
            steps{
                echo "Deploy to Nexus"
                // bat 'curl -v -u admin:admin --upload-file target\\msexcel-0.0.1-SNAPSHOT.jar http://localhost:8081/repository/suraj-maven-nexus/com/msoffice/msexcel/0.0.1/msexcel-0.0.1-SNAPSHOT.jar'                
            }
        }
        // (Optional) Verify the deployment by checking if the artifact exists in Nexus( HTTP request Plugins required )
        stage('05.Verifdy Deployment') {
            steps {
                echo "Verifying Deployment"
                // script {
                //     def nexusURL = "http://localhost:8081/repository/nexus-maven-jenkins/com/msoffice/msexcel/0.0.1/msexcel-0.0.1-SNAPSHOT.jar"
                //     def response = httpRequest(url: nexusURL, validResponseCodes: '200')
                //     echo "Deployment verification response: ${response}"
                // }
            }
        }
         
        
    }
}