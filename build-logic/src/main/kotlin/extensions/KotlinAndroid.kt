package extensions

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureAndroidKotlin(
    extension: AndroidApplicationExtension
) {

    extension.apply {

        defaultConfig {
            compileSdk = libs.findVersion("compileSdk").get().toString().toInt()

            targetSdk = libs.findVersion("targetSdk").get().toString().toInt()
            minSdk = libs.findVersion("minSdk").get().toString().toInt()
            applicationId = libs.findVersion("applicationId").get().toString()
            versionCode = libs.findVersion("versionCode").get().toString().toInt()
            versionName = libs.findVersion("versionName").get().toString()
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion =
                libs.findVersion("composeCompilerVersion").get().toString()
        }
        lint {
            disable += "NullSafeMutableLiveData"
            checkReleaseBuilds = false
            abortOnError = false
        }

    }

    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }
}

internal fun Project.configureAndroidKotlin(
    extension: AndroidLibraryExtension
) {
    extension.apply {
        compileSdk = libs.findVersion("compileSdk").get().toString().toInt()
        buildFeatures {
            buildConfig = true
        }


        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }
}