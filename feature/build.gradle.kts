plugins {
    id("fluxynews.android.library")
    id("fluxynews.android.hilt")
}

android {
    namespace = "com.example.feature"

}

dependencies {

    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.foundation.layout)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)
    implementation(libs.play.services.location)
    add("implementation", project(":domain"))

}