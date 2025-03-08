import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    application
    kotlin("jvm") version "2.1.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("org.graalvm.buildtools.native") version "0.10.5"
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

// https://graalvm.github.io/native-build-tools/latest/gradle-plugin.html#configure-native-image
// Install visual studio https://www.graalvm.org/latest/getting-started/windows/
// Run call "C:\Program Files\Microsoft Visual Studio\2022\Community\VC\Auxiliary\Build\vcvars64.bat" > nul
// On linux run java -jar -agentlib:native-image-agent=config-output-dir=src/main/resources/META-INF/native-image build/libs/xlsx2json-1.0-SNAPSHOT-all.jar src/test/resources/input.xlsx out.json
graalvmNative {
    // toolchainDetection.set(true)
    binaries {
        named("main") {
            useFatJar.set(true)
            buildArgs.add("-H:-CheckToolchain")
            // CP1252 is missing
            // https://github.com/apache/poi/blob/trunk/poi/src/main/java/org/apache/poi/poifs/filesystem/FileMagic.java#L133
            buildArgs.add("-H:+AddAllCharsets")
            configurationFileDirectories.from(file("src/main/resources/META-INF/native-image/"))
        }
    }
}
