plugins {
    alias(libs.plugins.p.core)
    id("fluxynews.android.hilt")

}

android {
    namespace = "com.example.core"
}

dependencies {
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.core.ktx)
}