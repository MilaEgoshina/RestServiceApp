pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                git branch: 'main', url: 'https://github.com/MilaEgoshina/RestServiceApp'

                // Run Maven on a Unix agent.
                sh "mvn -DskipTests=true package"
                sh "ls -l target"

                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }
        }
        stage('Deploy') {
            steps {
                // Copy the WAR file to a remote server
                // Replace with your actual remote server information
                sshServer remoteServer: [
                    name: 'milav1',
                    host: '127.0.0.1',
                    user: 'milav1',
                    password: 'password',
                    port: 22 // Default SSH port
                ] {
                    sh "cp /var/lib/jenkins/workspace/RestService/target/RestServiceApp-1.0-SNAPSHOT.war /opt/tomcat/webapps/"
                    //sh "sudo systemctl restart tomcat" // Optional: restart Tomcat on the remote server
                }
            }
        }
    }
}
