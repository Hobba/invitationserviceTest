#!/usr/bin/env groovy

pipeline {
    agent any

    tools {
        maven 'maven-3.5.0'
    }
    environment {
        TOKEN_KEY = credentials('TOKEN_KEY')
    }
    stages {
        stage ('Initialize') {
            steps {
                sh '''               
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }
        stage('Build') { 
            steps { 
                sh '''
                export SPRING_PROFILES_ACTIVE=production
                mvn install
                '''
            }
        }
          stage('Test'){
            steps {
                sh '''
                export SPRING_PROFILES_ACTIVE=production
                mvn test
                '''
            }
        }        
        stage('DeployDev'){
            steps {
                sh '''
                JAR_NAME=invitationService-0.0.1-SNAPSHOT.jar
                scp -o StrictHostKeyChecking=no -i /var/jenkins_home/.ssh/id_rsa target/${JAR_NAME} centos@194.45.211.158:/home/centos/${JAR_NAME}
                scp -o StrictHostKeyChecking=no -i /var/jenkins_home/.ssh/id_rsa devStart.sh centos@194.45.211.158:/home/centos/dev${JAR_NAME}Start.sh
                ssh -o StrictHostKeyChecking=no -i /var/jenkins_home/.ssh/id_rsa centos@194.45.211.158 chmod +x /home/centos/dev${JAR_NAME}Start.sh
                ssh -o StrictHostKeyChecking=no -i /var/jenkins_home/.ssh/id_rsa centos@194.45.211.158 /home/centos/dev${JAR_NAME}Start.sh ${JAR_NAME} ${TOKEN_KEY}
                '''
            }
        }
    }
}
