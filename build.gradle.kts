plugins {
    kotlin("jvm") version "1.8.20"
    id("com.github.johnrengelman.shadow") version "8.1.1"
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
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("me.tomasan7.jecnatimetabletogcalendar.MainKt")
}
