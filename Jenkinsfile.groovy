node {

    def mvnHome = tool 'Maven'

    try {
        stage("Checkout repository") {
            checkout scm
        }

        stage("unit test") {
            sh "${mvnHome}/bin/mvn clean test"
        }

        stage("integration test") {
            sh "${mvnHome}/bin/mvn test-compile failsafe:integration-test"
        }

        stage("build artifact") {
            sh "${mvnHome}/bin/mvn package"
        }
        stage("Store artifact") {
            archiveArtifacts artifacts: 'target/zip-service-jar-with-dependencies.jar', fingerprint: true
        }
    } finally {
        junit 'target/surefire-reports/**/*.xml'
        junit 'target/failsafe-reports/**/*.xml'
        step([$class: 'JacocoPublisher'])
    }

}