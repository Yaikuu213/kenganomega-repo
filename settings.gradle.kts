pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        // On définit les versions des moteurs ici
        id("com.android.application") version "8.2.2" apply false
        id("com.android.library") version "8.2.2" apply false
        id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "kenganomega-repo"

// On inclut ton extension
include(":extensions-en-kenganmanga")
project(":extensions-en-kenganmanga").projectDir = File(rootProject.projectDir, "extensions/en/kenganmanga")
