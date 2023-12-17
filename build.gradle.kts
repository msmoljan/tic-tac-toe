plugins {
    kotlin("jvm") version "1.9.20"
    application
}

group = "dev.matko"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("dev.matko.tictactoe.MainKt")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "dev.matko.tictactoe.MainKt"
    }

    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}