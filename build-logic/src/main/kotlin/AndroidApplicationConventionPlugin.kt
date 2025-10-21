import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile


class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        with(target) {
            pluginManager.apply("com.android.application")
            pluginManager.apply("org.jetbrains.kotlin.android")
            target.pluginManager.apply("org.jetbrains.kotlin.plugin.compose") // Compose plugin


            extensions.configure<ApplicationExtension> {
                compileSdk = 36
                defaultConfig {
                    minSdk = 26
                    targetSdk = 36
                    applicationId = "com.example.fluxynews"
                    versionCode = 1
                    versionName = "1.0"
                }
                buildFeatures {
                    compose = true
                }
                composeOptions {
                    kotlinCompilerExtensionVersion = "1.5.15" // istediğin Compose sürümü
                }
                // Java compile task JVM target 17
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }
            }
            tasks.withType<KotlinJvmCompile>().configureEach {
                compilerOptions {
                    jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
                }
            }
        }
    }
}