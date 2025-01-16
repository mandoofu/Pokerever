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
        classpath(libs.hilt.android.gradle.plugin.v244)
        //classpath("com.google.gms:google-services:4.4.2") // Google Services 플러그인
        classpath(libs.google.services)
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
   // alias(libs.plugins.google.services) apply false
}

