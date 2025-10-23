package plugins

import extensions.addCommonNetworkDependencies
import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidCoreConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("fluxynews.android.library")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            dependencies{
                addCommonNetworkDependencies(this@with)
            }


        }
    }
} 