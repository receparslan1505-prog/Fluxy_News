plugins {
    alias(libs.plugins.p.core)
    id("fluxynews.android.hilt")

}

android {
    namespace = "com.example.core"
}
dependencies {
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.datastore.preferences.core)
}

