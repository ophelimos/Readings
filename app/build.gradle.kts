plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "uk.co.tekkies.readings"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "uk.co.tekkies.readings"
        minSdk = 34
        targetSdk = 36
        versionCode = 4
        versionName = "4.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.fragment)
    implementation(libs.viewpager2)
    implementation(libs.annotation)
    implementation(libs.preferencemanager)
}