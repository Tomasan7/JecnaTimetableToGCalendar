plugins {
    kotlin("jvm") version "1.8.20"
    application
}

group = "me.tomasan7"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.Tomasan7.JecnaAPI:jecnaapi:3.2.1")

    /*val ktorVersion = "2.3.0"
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")*/
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
}
