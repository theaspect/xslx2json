import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    application
    kotlin("jvm") version "2.1.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.blzr"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.monitorjbl:xlsx-streamer:2.1.0")

    implementation("com.fasterxml.jackson.core:jackson-core:2.18.3")

    testImplementation("io.kotest:kotest-framework-api-jvm:5.9.0")
    testImplementation("io.kotest:kotest-runner-junit5:5.9.0")
}

application {
    mainClass.set("me.blzr.xlsx2json.MainKt")
}

kotlin {
    jvmToolchain {
        this.languageVersion = JavaLanguageVersion.of(17)
    }
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
        javaParameters = true
    }
}

tasks.test {
    useJUnitPlatform()
}
