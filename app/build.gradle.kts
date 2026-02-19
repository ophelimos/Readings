plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "org.navigatebyfaith.rrreadings"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "org.navigatebyfaith.rrreadings"
        minSdk = 26
        targetSdk = 36
        versionCode = 134040201
        versionName = "4.2.1"

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
    implementation(libs.viewpager)
    implementation(libs.annotation)
    implementation(libs.preferencemanager)
}
