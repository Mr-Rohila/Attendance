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
             	catchError(buildResult: 'UNSTABLE') {
             	     bat "nssm stop Attendance"
             	     sleep time: 5, unit: 'SECONDS'
             	}
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
           currentBuild.result = 'SUCCESS'
        }
    }
}