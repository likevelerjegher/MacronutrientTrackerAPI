plugins {
    id("java")
    id("org.sonarqube") version "4.4.1.3373"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}
sonar {
    properties {
        property("sonar.projectKey", "likevelerjegher_kaloriinnhold")
        property("sonar.organization", "likevelerjegher")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}
tasks.test {
    useJUnitPlatform()
}