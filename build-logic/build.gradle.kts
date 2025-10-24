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
    implementation(libs.android.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.kotlin.serialization.plugin)
    implementation(libs.ksp.gradle.plugin)
    implementation(libs.hilt.gradle.plugin)
}
