import com.moowork.gradle.node.npm.NpmTask

plugins {
    id("java")
    kotlin("jvm") version "1.6.10"
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("com.moowork.node") version "1.3.1"
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

task("installFrontend", type = NpmTask::class) {
    setArgs(listOf("install"))
    setWorkingDir(file("frontend"))
}

task("buildFrontend", type = NpmTask::class) {
    dependsOn("installFrontend")
    setArgs(listOf("run", "build"))
    setWorkingDir(file("frontend"))
}

task("copyFrontendToStatic", type = Copy::class) {
    dependsOn("buildFrontend")
    from("frontend/build")
    into("src/main/resources/static")
}

task("buildFrontendAssets") {
    dependsOn("copyFrontendToStatic")
}


