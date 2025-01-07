// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.gradle) // Android Gradle Plugin
        classpath(libs.kotlin.gradle.plugin) // Kotlin Plugin
        classpath(libs.hilt.android.gradle.plugin.v244)
        classpath(libs.google.services) // Google Services Plugin
    }
}
