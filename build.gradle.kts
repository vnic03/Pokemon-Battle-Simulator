import com.moowork.gradle.node.npm.NpmTask

plugins {
    id("java")
    kotlin("jvm") version "1.6.10"
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("com.moowork.node") version "1.3.1"
    id("org.springframework.boot") version "2.6.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

javafx {
    version="21.0.1"
    modules = listOf("javafx.controls", "javafx.fxml")
}


node {
    version ="20.10.0"
    npmVersion = "10.2.3"
    download = true
}



group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(kotlin("stdlib"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.register<JavaExec>("run") {
     mainClass.set("org.example.Main")
}


tasks.test {
    useJUnitPlatform()
}

sourceSets {
    main {
        resources {
            srcDirs("src/main/resources")
        }
    }
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}



