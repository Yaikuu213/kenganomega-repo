pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    versionCatalogs {
        create("libs") {
            from(files(rootProject.projectDir.resolve("libs.versions.toml")))
        }
        create("tachiyomi") {
            from(files(rootProject.projectDir.resolve("tachiyomi.versions.toml")))
        }
    }

    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
        maven { url = uri("https://jitpack.io") }
    }
}

plugins {
    id("de.fayard.refreshVersions") version "+"
    id("org.gradle.toolchains.foojay-resolver-convention") version "+"
}

rootProject.apply {
    name = "tachiyomi-extensions-template"
}

rootProject.projectDir.resolve("build-src").apply {
    includeBuild(resolve("conventions"))
    includeBuild(resolve("github-api"))
}

// On simplifie la recherche pour éviter les bugs
include(":extensions-en-kenganmanga")
project(":extensions-en-kenganmanga").projectDir = File(rootProject.projectDir, "extensions/en/kenganmanga")

includeAllSubprojectsIn(rootProject.projectDir.resolve("lib"), "lib")
includeAllSubprojectsIn(rootProject.projectDir.resolve("multisrc"), "multisrc")

fun includeAllSubprojectsIn(dir: File, prefix: String?) {
    if (!dir.exists() || !dir.isDirectory) return
    dir.listFiles()?.filter { it.isDirectory }?.forEach { inclusion ->
        val path = if (prefix == null) ":${inclusion.name}" else ":$prefix-${inclusion.name}"
        include(path)
        project(path).projectDir = inclusion
    }
}
