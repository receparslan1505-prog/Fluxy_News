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
dependencies {
    // Android Gradle Plugin
    implementation("com.android.tools.build:gradle:8.13.0")
    // Kotlin Gradle Plugin
    implementation(kotlin("gradle-plugin", version = "2.0.21"))
}
