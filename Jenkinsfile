pipeline {
    agent any

    stages {
        stage('Addition') {
            steps {
                script {
                    def a = 10
                    def b = 30
                    def sum = a + b
                    echo "Addition of ${a} and ${b} is: ${sum}"
                }
            }
        }

        stage('Subtraction') {
            steps {
                script {
                    def a = 10
                    def b = 30
                    def difference = b - a
                    echo "Subtraction of ${a} from ${b} is: ${difference}"
                }
            }
        }

        stage('Multiplication') {
            steps {
                script {
                    def a = 10
                    def b = 30
                    def product = a * b
                    echo "Multiplication of ${a} and ${b} is: ${product}"
                }
            }
        }
    }
}
