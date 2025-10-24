import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
            implementationClass = "plugins.AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "fluxynews.android.library"
            implementationClass = "plugins.AndroidLibraryConventionPlugin"
        }
        register("androidHilt") {
            id = "fluxynews.android.hilt"
            implementationClass = "plugins.AndroidHiltConventionPlugin"
        }
        register("androidCore") {
            id = "p.core"
            implementationClass = "plugins.AndroidCoreConventionPlugin"
        }
    }
}

dependencies {
    // Android Gradle Plugin
    implementation("com.android.tools.build:gradle:8.9.1")

    implementation("org.jetbrains.kotlin:kotlin-serialization:2.0.21")


    // Kotlin Gradle Plugin
    implementation(kotlin("gradle-plugin", version = "2.0.21"))
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.51")
    implementation("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.0.21-1.0.28")

}
