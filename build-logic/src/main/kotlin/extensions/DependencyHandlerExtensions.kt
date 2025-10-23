package extensions

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.kotlin

internal fun DependencyHandler.addCommonAndroidDependencies(project: Project) {
    implementation(libs(project).findLibrary("androidx.core.ktx").get())
    implementation(libs(project).findLibrary("androidx.appcompat").get())
    implementation(libs(project).findLibrary("material").get())
}

internal fun DependencyHandler.addCommonComposeDependencies(project: Project) {
    implementation(platform(libs(project).findLibrary("compose.bom").get()))
    implementation(libs(project).findLibrary("androidx.ui").get())
    implementation(libs(project).findLibrary("androidx.ui.graphics").get())
    implementation(libs(project).findLibrary("androidx.ui.tooling.preview").get())
    implementation(libs(project).findLibrary("androidx.material3").get())
    implementation(libs(project).findLibrary("androidx.activity.compose").get())
    implementation(libs(project).findLibrary("androidx.lifecycle.runtime.ktx").get())
    implementation(libs(project).findLibrary("coil.compose").get())
    implementation(libs(project).findLibrary("coil.gif").get())
    
    debugImplementation(libs(project).findLibrary("androidx.ui.tooling").get())
    debugImplementation(libs(project).findLibrary("androidx.ui.test.manifest").get())
}

internal fun DependencyHandler.addCommonHiltDependencies(project: Project) {
    implementation(libs(project).findLibrary("hilt.android").get())
    ksp(libs(project).findLibrary("hilt.compiler").get())
}

internal fun DependencyHandler.addCommonNetworkDependencies(project: Project) {
    implementation(libs(project).findLibrary("retrofit").get())
    implementation(libs(project).findLibrary("okhttp").get())
    implementation(libs(project).findLibrary("retrofit.converter").get())
    implementation(libs(project).findLibrary("okhttp.logging.interceptor").get())
    implementation(libs(project).findLibrary("gson").get())
    debugImplementation(libs(project).findLibrary("chucker").get())
    releaseImplementation(libs(project).findLibrary("chucker-no-op").get())
}


// build.gradle.kts veya ayrı bir test-deps.gradle.kts içine yazabilirsin

internal fun DependencyHandler.addAllTestDependencies() {
    // -------------------------------
    // ✅ JVM Unit Test Dependencies
    // -------------------------------
    add("testImplementation", kotlin("test"))
    add("testImplementation", "org.robolectric:robolectric:4.11.1")
    add("testImplementation", "junit:junit:4.13.2")
    add("testImplementation", "org.mockito:mockito-core:5.3.1")
    add("testImplementation", "org.mockito.kotlin:mockito-kotlin:5.2.1")
    add("testImplementation", "io.mockk:mockk:1.13.7")
    add("testImplementation", "com.google.truth:truth:1.1.5")
    add("testImplementation", "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    add("testImplementation", "app.cash.turbine:turbine:1.0.0")
    add("testImplementation", "androidx.work:work-testing:2.9.0")

    // -------------------------------
    // ✅ Android Instrumented Test Dependencies
    // -------------------------------
    add("androidTestImplementation", "androidx.test:core:1.6.0")
    add("androidTestImplementation", "androidx.test:runner:1.6.0")
    add("androidTestImplementation", "androidx.test:rules:1.6.0")
    add("androidTestImplementation", "androidx.test.ext:junit:1.2.1")

    // Compose UI Test
    add("androidTestImplementation", "androidx.compose.ui:ui-test-junit4:1.5.4")
    add("debugImplementation", "androidx.compose.ui:ui-test-manifest:1.5.4")

    // Mockito (instrumented taraf)
    add("androidTestImplementation", "org.mockito:mockito-core:5.3.1")
    add("androidTestImplementation", "org.mockito.kotlin:mockito-kotlin:5.2.1")

    // Hilt Testing
    add("androidTestImplementation", "com.google.dagger:hilt-android-testing:2.48")
    add("ksp", "com.google.dagger:hilt-android-compiler:2.48")
}



// Utility functions
fun DependencyHandler.implementation(dependency: Any) {
    add("implementation", dependency)
}

fun DependencyHandler.debugImplementation(dependency: Any) {
    add("debugImplementation", dependency)
}

fun DependencyHandler.releaseImplementation(dependency: Any) {
    add("releaseImplementation", dependency)
}

private fun DependencyHandler.ksp(dependency: Any) {
    add("ksp", dependency)
}

internal fun libs(project: Project): VersionCatalog = project.extensions
    .getByType(VersionCatalogsExtension::class.java)
    .named("libs") 