import com.android.build.api.dsl.ApplicationExtension
import extensions.AndroidCommonVersions
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
                with(AndroidCommonVersions) {

                    compileSdk = COMPILE_SDK
                    defaultConfig {
                        minSdk = MIN_SDK
                        targetSdk = TARGET_SDK
                        applicationId = APPLICATION_ID
                        versionCode = VERSION_CODE
                        versionName = VERSION_NAME
                    }
                }
                buildFeatures {
                    compose = true
                }
                composeOptions {
                    kotlinCompilerExtensionVersion =
                        AndroidCommonVersions.COMPOSE_COMPILER_VERSION // istediğin Compose sürümü
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