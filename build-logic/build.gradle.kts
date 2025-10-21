plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "fluxynews.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "fluxynews.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
    }
}
