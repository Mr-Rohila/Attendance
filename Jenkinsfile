pipeline {
    agent any
    
    environment {
        MAVEN_HOME = tool 'Maven'
    }

    stages {
        stage('Build') {
            steps {
                bat "\"${MAVEN_HOME}\\bin\\mvn\" clean package"
            }
        }

        stage('Stop Service') {
            steps {
                bat "nssm stop Attendance"
            }
        }

 		stage('Copy Jar File') {
            steps {
                bat "copy /Y target\\Attendance.jar G:\\HRMS_API\\Attendance"
            }
        }

        stage('Run Employee Service') {
            steps {            
                bat "nssm start Attendance"
            }
        }
    }
}