package plugins

import com.android.build.api.dsl.ApplicationExtension
import extensions.configureAndroidKotlin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        with(target) {
            pluginManager.apply("com.android.application")
            pluginManager.apply("org.jetbrains.kotlin.android")
            target.pluginManager.apply("org.jetbrains.kotlin.plugin.compose") // Compose plugin

            extensions.configure<ApplicationExtension> {
                configureAndroidKotlin(this)
            }
        }
    }
}