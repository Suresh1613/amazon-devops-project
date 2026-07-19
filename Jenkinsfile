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
                    withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
                        sh '''
                        mvn sonar:sonar \
                        -Dsonar.projectKey=amazon-app \
                        -Dsonar.host.url=http://172.31.26.56:9000 \
                        -Dsonar.login=$SONAR_TOKEN
                        '''
                    }
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
