plugins {
    // Android plugin (already present via version catalog)
    alias(libs.plugins.android.application) apply false

    // ✅ REQUIRED: Declare Google Services plugin WITH VERSION
    id("com.google.gms.google-services") version "4.4.2" apply false
}
