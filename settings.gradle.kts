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
    }
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "kenganomega-repo"
rootProject.buildFileName = "env.build.gradle.kts"

// LA MAGIE EST ICI : On rebranche les outils de création de JSON officiels
rootProject.projectDir.resolve("build-src").apply {
    if (exists()) {
        includeBuild(resolve("conventions"))
    }
}

include(":extensions-en-kenganmanga")
project(":extensions-en-kenganmanga").projectDir = File(rootProject.projectDir, "extensions/en/kenganmanga")
