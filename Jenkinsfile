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

        stage('Build Docker Image') {
            steps {
                script {
                    // Build the Docker image using the Dockerfile in the repo
                    docker.build("${DOCKER_IMAGE}")
                }
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    // Use docker-compose to spin up the environment and run tests
                    sh 'docker-compose up --abort-on-container-exit'
                }
            }
        }

        stage('Tear Down') {
            steps {
                script {
                    // Clean up the containers after the tests
                    sh 'docker-compose down'
                }
            }
        }
    }

    post {
        always {
            // Archive test results (optional)
            archiveArtifacts artifacts: '**/target/surefire-reports/*.xml', allowEmptyArchive: true

            // Clean up any dangling Docker images
            sh 'docker system prune -f'
        }
        failure {
            // Notify team in case of failure (optional)
            mail to: 'abmike268@gmail.com',
                 subject: "Build Failed in Jenkins",
                 body: "Build ${env.BUILD_NUMBER} failed. Check the Jenkins logs for more details."
        }
    }
}

