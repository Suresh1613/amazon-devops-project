pipeline {

    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Backend Build') {
            steps {
                dir('backend') {
                    sh 'mvn clean package'
                }
            }
        }

        stage('Frontend Build') {
            steps {
                dir('frontend') {
                    sh 'npm install'
                    sh 'npm run build'
                }
            }
        }

        stage('SonarQube Scan') {
            steps {
                dir('backend') {
                    sh '''
                    mvn sonar:sonar \
                    -Dsonar.projectKey=amazon-app \
                    -Dsonar.host.url=http://YOUR_SONAR_SERVER:9000
                    '''
                }
            }
        }

        stage('Upload to Nexus') {
            steps {
                sh '''
                curl -u admin:PASSWORD \
                --upload-file backend/target/amazon-app-1.0.0.jar \
                http://YOUR_NEXUS:8081/repository/maven-releases/amazon-app.jar
                '''
            }
        }

        stage('Deploy Frontend') {
            steps {
                sh '''
                cp -r frontend/dist/* \
                /opt/tomcat/webapps/ROOT/
                '''
            }
        }

    }

}
