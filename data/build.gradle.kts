import extensions.addCommonNetworkDependencies

plugins {
    id("fluxynews.android.library")
    id("fluxynews.android.hilt")

}

android {
    namespace = "com.example.data"
    dependencies {
        add("implementation", project(":domain"))

    }
}
dependencies {
    implementation(libs.play.services.contextmanager)
    addCommonNetworkDependencies(project)
}

