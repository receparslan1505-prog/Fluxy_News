plugins {
    id("fluxynews.android.library")
    id("fluxynews.android.hilt")

}

android {
    namespace = "com.example.core"
}

dependencies {
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}