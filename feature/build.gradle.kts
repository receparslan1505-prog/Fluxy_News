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
}