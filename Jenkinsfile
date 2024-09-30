pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'cucumber-test-runner' // Name of the Docker image
        DOCKER_COMPOSE_FILE = 'docker-compose.yml' // Path to docker-compose.yml
    }

    stages {
        stage('Clone Repository') {
            steps {
                // Clone the Git repository
                git url: 'https://github.com/Michael-B-Ahiale/Invoice_App_BDD_Cucumber_Containerized.git', branch: 'main'
            }
        }

        stage('Build and Run Tests with Docker Compose') {
            steps {
                script {
                    // Build the Docker image and run the tests using docker-compose
                    bat "docker-compose -f ${DOCKER_COMPOSE_FILE} up --build --abort-on-container-exit"
                }
            }
        }

        stage('Tear Down') {
            steps {
                script {
                    // Clean up the containers after the tests
                    bat "docker-compose -f ${DOCKER_COMPOSE_FILE} down"
                }
            }
        }
    }

    post {
        always {
            // Archive test results (optional)
            archiveArtifacts artifacts: '**/target/surefire-reports/*.xml', allowEmptyArchive: true

            // Clean up any dangling Docker images
            bat 'docker system prune -f'
        }
        failure {
            // Notify team in case of failure (optional)
            mail to: 'abmike268@gmail.com',
                 subject: "Build Failed in Jenkins",
                 body: "Build ${env.BUILD_NUMBER} failed. Check the Jenkins logs for more details."
        }
    }
}