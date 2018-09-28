node {

    def mvnHome = tool 'Maven'

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

}