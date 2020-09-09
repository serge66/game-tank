import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.0"
    application
}

application{
    mainClassName="com.github.serge66.AppKt"
}

dependencies {
    org.jetbrains.kotlin.gradle.utils.IMPLEMENTATION(kotlin("stdlib-jdk8"))
    org.jetbrains.kotlin.gradle.utils.IMPLEMENTATION("com.github.shaunxiao:kotlinGameEngine:v0.0.4")
}
repositories {
    mavenCentral()
    maven{
        setUrl("https://jitpack.io")
    }
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}