pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test') {
            agent {
                docker {
                    image 'maven:3.8.4-openjdk-21'
                    args '-v $HOME/.m2:/root/.m2'
                }
            }
            steps {
                sh 'mvn clean install'
                sh 'mvn test'
            }
        }

        stage('Generate Reports') {
            agent {
                docker {
                    image 'maven:3.8.4-openjdk-21'
                    args '-v $HOME/.m2:/root/.m2'
                }
            }
            steps {
                sh 'mvn verify'
            }
        }

        stage('Publish Test Results') {
            steps {
                junit '**/target/surefire-reports/*.xml'
                publishHTML target: [
                    reportName : 'HTML Report',
                    reportDir  : 'target/cucumber-reports',
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
                body: '''<p><font color='green'>SUCCESS:</font> The build ${currentBuild.fullDisplayName} passed.</p>
                        <p>See the attached test results and report for more details.</p>''',
                to: 'abmike268@gmail.com',
                recipientProviders: [[$class: 'DevelopersRecipientProvider']],
                attachLog: true,
                attachmentsPattern: '**/target/surefire-reports/*.xml, **/target/cucumber-reports/index.html'
            )
        }
        failure {
            emailext(
                subject: 'Build Failure: ${currentBuild.fullDisplayName}',
                body: '''<p><font color='red'>FAILURE:</font> The build ${currentBuild.fullDisplayName} failed.</p>
                        <p>See the attached test results and report for more details.</p>''',
                to: 'abmike268@gmail.com',
                recipientProviders: [[$class: 'DevelopersRecipientProvider']],
                attachLog: true,
                attachmentsPattern: '**/target/surefire-reports/*.xml, **/target/cucumber-reports/index.html'
            )
        }
    }
}