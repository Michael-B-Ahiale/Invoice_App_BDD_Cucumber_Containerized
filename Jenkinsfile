pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'openjdk:21-slim'
        TEST_RESULTS_PATH = 'target/surefire-reports'
        HTML_REPORT_PATH = 'target/cucumber-reports'
    }

    stages {
        stage('Checkout') {
            steps {
                // Check out code from GitHub
                git branch: 'main', url: 'https://github.com/Michael-B-Ahiale/Invoice_App_BDD_Cucumber_Containerized.git'
            }
        }

        stage('Build') {
            steps {
                // Build the Maven project inside a Docker container
                sh 'docker run --rm -v "$PWD":/app -w /app ${DOCKER_IMAGE} mvn clean install'
            }
        }

        stage('Run Tests') {
            steps {
                // Run tests inside Docker
                sh 'docker run --rm -v "$PWD":/app -w /app ${DOCKER_IMAGE} mvn test'
            }
        }

        stage('Generate Reports') {
            steps {
                // Generate Cucumber HTML reports
                sh 'docker run --rm -v "$PWD":/app -w /app ${DOCKER_IMAGE} mvn verify'
            }
        }

        stage('Publish Test Results') {
            steps {
                // Publish test results in Jenkins UI and HTML report
                junit '**/target/surefire-reports/*.xml'
                publishHTML target: [
                    reportName : 'HTML Report',
                    reportDir  : "${HTML_REPORT_PATH}",
                    reportFiles: 'index.html',
                    keepAll    : true,
                    alwaysLinkToLastBuild: true,
                    allowMissing: true
                ]
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: '**/target/surefire-reports/*.xml', allowEmptyArchive: true
        }
        success {
            emailext(
                subject: 'Build Success: ${currentBuild.fullDisplayName}',
                body: """<p><font color='green'>SUCCESS:</font> The build ${currentBuild.fullDisplayName} passed.</p>
                        <p>See the attached test results and report for more details.</p>""",
                recipientProviders: [[$class: 'DevelopersRecipientProvider']],
                attachLog: true,
                attachmentsPattern: '**/target/surefire-reports/*.xml, **/target/cucumber-reports/index.html'
            )
        }
        failure {
            emailext(
                subject: 'Build Failure: ${currentBuild.fullDisplayName}',
                body: """<p><font color='red'>FAILURE:</font> The build ${currentBuild.fullDisplayName} failed.</p>
                        <p>See the attached test results and report for more details.</p>""",
                recipientProviders: [[$class: 'DevelopersRecipientProvider']],
                attachLog: true,
                attachmentsPattern: '**/target/surefire-reports/*.xml, **/target/cucumber-reports/index.html'
            )
        }
    }
}
