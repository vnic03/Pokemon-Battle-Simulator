import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    kotlin("jvm") version "1.6.10"
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("com.github.johnrengelman.shadow") version "7.1.0"
    id("application")
    id("groovy")
}

javafx {
    version="21.0.1"
    modules = listOf("javafx.controls", "javafx.fxml", "javafx.media")
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
    implementation("org.openjfx:javafx-media:21.0.1")
    implementation("org.json:json:20240303")
    implementation("org.codehaus.groovy:groovy:3.0.21")
    implementation("org.codehaus.groovy:groovy-jsr223:3.0.21")
    implementation("org.apache.logging.log4j:log4j-core:2.17.1")
    implementation("org.apache.logging.log4j:log4j-api:2.17.1")
    implementation("org.slf4j:slf4j-api:1.7.32")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.17.1")
}

application {
    mainClass.set("org.example.MainApplication")
}

tasks.withType<ShadowJar> {
    archiveBaseName.set("Pokemon-Battle-Simulator")
    archiveClassifier.set("")
    archiveVersion.set("1.0")
}

tasks.test {
    useJUnitPlatform()
}

sourceSets {
    main {
        groovy {
            srcDirs("scripts")
        }
        resources {
            srcDirs("src/main/resources")
        }
    }
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}