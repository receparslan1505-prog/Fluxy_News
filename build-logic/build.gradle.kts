plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}
gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "fluxynews.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "fluxynews.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
    }
}
gradlePlugin {
    plugins {
        register("androidHilt") {
            id = "fluxynews.android.hilt"
            implementationClass = "plugins.AndroidHiltConventionPlugin"
        }
    }
}
dependencies {
    // Android Gradle Plugin
    implementation("com.android.tools.build:gradle:8.13.0")
    // Kotlin Gradle Plugin
    implementation(kotlin("gradle-plugin", version = "2.0.21"))
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.57.2")
}
