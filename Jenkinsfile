pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'echo "RUN BUILD"'
                sh './gradlew build -x test'
            }
        }

        stage('Unit tests') {
            steps {
                dir('build/test-results/test') {
                    sh 'echo "REMOVE OLD TESTS RESULTS"'
                    deleteDir()
                }

                sh 'echo "RUN UNIT TESTS"'
                sh './gradlew test'
            }
        }

        stage('Publish test results') {
            steps {
                sh 'echo "PUBLISH TESTS RESULTS"'
                junit '**/test-results/test/*.xml'
            }
        }
    }
}