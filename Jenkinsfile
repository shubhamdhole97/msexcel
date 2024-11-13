node {
    stage('Clone') {
        steps {
            git branch: 'main', credentialsId: 'shubhamdhole97jenkins', url: 'git@github.com:shubhamdhole97/msexcel.git
        }
    }
    stage('Build') {
        steps {
            echo "Building"
        }
    }
    stage('Test') {
        steps {
            echo "Testing"
        }
    }
}

}