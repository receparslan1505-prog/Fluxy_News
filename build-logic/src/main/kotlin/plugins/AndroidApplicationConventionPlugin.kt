package plugins

import com.android.build.api.dsl.ApplicationExtension
import extensions.addNeedDependencies
import extensions.configureAndroidKotlin
import extensions.implementation
import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.application")
            pluginManager.apply("org.jetbrains.kotlin.android")
            pluginManager.apply("com.google.devtools.ksp")
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            // Android config
            extensions.configure<ApplicationExtension> {
                configureAndroidKotlin(this)
            }

            // Normal implementation bağımlılıklarını ekle
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
