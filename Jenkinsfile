#!/usr/bin/env groovy

// Global scope required for multi-stage persistence
def buildInfo = Artifactory.newBuildInfo()
def agentSbtVersion = 'sbt_1-2-4'

pipeline {
    libraries {
        lib('jenkins-pipeline-shared')
    }
    environment {
        SVC_NAME = "sbr-int-test"
        ORG = "SBR"
        ENV_NAME = "dev"
    }
    options {
        skipDefaultCheckout()
        buildDiscarder(logRotator(numToKeepStr: '30', artifactNumToKeepStr: '30'))
        timeout(time: 1, unit: 'HOURS')
        ansiColor('xterm')
    }
    agent { label 'download.jenkins.slave' }
    stages {
        stage('Checkout') {
            agent { label 'download.jenkins.slave' }
            steps {
                deleteDir()
                checkout scm
                dir('config') {
                    checkout poll: false, scm:[$class: 'GitSCM', branches: [[name: 'dev-int-test']], userRemoteConfigs: [[credentialsId: 'JenkinsSBR__gitlab', url: "${GITLAB_URL}/StatBusReg/${env.SVC_NAME}.git"]]]
                }
                sh "cp -v config/${env.ENV_NAME}/application.conf src/test/resources"
                script {
                    buildInfo.name = "${SVC_NAME}"
                    buildInfo.number = "${BUILD_NUMBER}"
                    buildInfo.env.collect()
                }
                colourText("info", "BuildInfo: ${buildInfo.name}-${buildInfo.number}")
                stash name: 'Checkout'
            }
        }

        stage('Build'){
            agent { label "build.${agentSbtVersion}" }
            steps {
                unstash name: 'Checkout'
                sh "sbt compile test:compile"
            }
            post {
                success {
                    colourText("info","Stage: ${env.STAGE_NAME} successful!")
                }
                failure {
                    colourText("warn","Stage: ${env.STAGE_NAME} failed!")
                }
            }
        }

        stage('Test: Integration'){
            agent { label "build.${agentSbtVersion}" }
            steps {
                unstash name: 'Checkout'
                sh 'sbt test'
            }
            post {
                always {
                    junit '**/target/test-reports/*.xml'
                }
                success {
                    colourText("info","Stage: ${env.STAGE_NAME} successful!")
                }
                failure {
                    colourText("warn","Stage: ${env.STAGE_NAME} failed!")
                }
            }
        }
    }

    post {
        success {
            colourText("success", "All stages complete. Build was successful.")
            slackSend(
                color: "good",
                message: "${env.JOB_NAME} success: ${env.RUN_DISPLAY_URL}"
            )
        }
        unstable {
            colourText("warn", "Something went wrong, build finished with result ${currentResult}. This may be caused by failed tests, code violation or in some cases unexpected interrupt.")
            slackSend(
                color: "warning",
                message: "${env.JOB_NAME} unstable: ${env.RUN_DISPLAY_URL}"
            )
        }
        failure {
            colourText("warn","Process failed at: ${env.NODE_STAGE}")
            slackSend(
                color: "danger",
                message: "${env.JOB_NAME} failed at ${env.STAGE_NAME}: ${env.RUN_DISPLAY_URL}"
            )
        }
    }
}
