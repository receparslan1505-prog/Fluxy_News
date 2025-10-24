package plugins

import com.android.build.gradle.LibraryExtension
import extensions.addNeedDependencies
import extensions.configureAndroidKotlin
import extensions.implementation
import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            pluginManager.apply("org.jetbrains.kotlin.android")
            pluginManager.apply("com.google.devtools.ksp")

            extensions.configure<LibraryExtension> {
                val extension = extensions.getByType<com.android.build.api.dsl.LibraryExtension>()
                configureAndroidKotlin(extension)
                dependencies {
                    implementation(libs(project).findLibrary("hilt.android").get())
                    implementation(libs(project).findLibrary("androidx.core.ktx").get())
                }

                // ⚡ KSP dependency'sini güvenli ekle
                plugins.withId("com.google.devtools.ksp") {
                    dependencies {
                        add("ksp", libs(project).findLibrary("hilt.compiler").get())
                    }
                }

            }

        }
    }
}