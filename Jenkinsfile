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
             	     bat "net stop Attendance"
             	     sleep time: 20, unit: 'SECONDS'
             	     bat 'sc query Attendance | find "STATE" | find "STOPPED" && echo "Service stopped"'  
            }
        }

 		stage('Copy Jar File') {
            steps {
                bat "copy /Y target\\Attendance.jar G:\\HRMS_API\\Attendance"
            }
        }
    }
    post {
        always {
           bat "nssm start Attendance"
        }
    }
}