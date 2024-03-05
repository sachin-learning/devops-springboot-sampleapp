pipeline {
    agent any

    tools {
	    maven "Maven3"
	    jdk "OpenJDK17"
	}

    environment {
        DOCKER_IMAGE_NAME = "sachinlearning/springboot-sampleapp"//"${env.DOCKER_IMAGE_NAME}"
        DOCKER_REGISTRY_URL = "https://registry.hub.docker.com" //"${env.DOCKER_REGISTRY_URL}"
        DOCKER_REGISTRY_CREDENTIALS = "Dockerhub"//"${env.DOCKER_REGISTRY_CREDENTIALS}"
        GITHUB_REPO_CREDENTIALS_ID = "Github-sachin-learning"
    }
    
    stages {
        stage('Fetch Code') {
            steps {
                git credentialsId: "{$GITHUB_REPO_CREDENTIALS_ID}", branch: 'main', url: 'git@github.com:sachin-learning/devops-springboot-sampleapp.git'
            }
        }
        
        stage('Run Maven Tests') {
            steps {
                sh 'mvn test'
            }
        }
        
        stage('Checkstyle Analysis') {
            steps {
                sh 'mvn checkstyle:checkstyle'
                // Alternatively, you can use a Checkstyle plugin to parse the results and display them in Jenkins UI
            }
        }

        stage('SonarQube Scan') {
            environment {
                scannerHome = tool 'SonarQube'
                sonarqubeServer = 'sonarqube-local'
            }
            steps {
                withSonarQubeEnv("${sonarqubeServer}") {
                    sh '''
                        ${scannerHome}/bin/sonar-scanner \
                        -Dsonar.projectKey=sampleapp \
                        -Dsonar.projectName=sampleapp \
                        -Dsonar.projectVersion=1.0 \
                        -Dsonar.sources=src/ \
                        -Dsonar.java.binaries=target/test-classes/com/sachin/learning/sample1 \
                        -Dsonar.junit.reportPaths=target/surefire-reports \
                        -Dsonar.checkstyle.reportPaths=target/checkstyle-result.xml
                    '''
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Build Package') {
            steps {
                sh 'mvn package'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKER_IMAGE_NAME}" + ":$BUILD_NUMBER")
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    docker.withRegistry("${DOCKER_REGISTRY_URL}", "${DOCKER_REGISTRY_CREDENTIALS}") {
                        docker.image("${DOCKER_IMAGE_NAME}" + ":$BUILD_NUMBER").push("$BUILD_NUMBER")
                        docker.image("${DOCKER_IMAGE_NAME}" + ":$BUILD_NUMBER").push('latest')
                    }
                }
            }
        }
    }
}