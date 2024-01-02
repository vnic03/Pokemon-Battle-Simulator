plugins {
    id("java")
    kotlin("jvm") version "1.6.10"
    id("org.openjfx.javafxplugin") version "0.1.0"
}

javafx {
    version="21.0.1"
    modules = listOf("javafx.controls", "javafx.fxml")
}



group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")


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

