// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath(libs.gradle) // Android Gradle Plugin
        classpath(libs.kotlin.gradle.plugin) // Kotlin Plugin
        classpath(libs.google.services)
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.com.google.devetool.ksp) apply false
    alias(libs.plugins.hilt.android) apply false
}

