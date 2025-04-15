plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "ir.ha.goodfeeling"
    compileSdk = 35

    defaultConfig {
        applicationId = "ir.ha.goodfeeling"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    // GoogleServices (location)
    implementation(libs.play.services.location)


    implementation("io.coil-kt:coil-compose:2.5.0")

    implementation ("androidx.compose.material3:material3:1.2.0")

    implementation ("androidx.navigation:navigation-compose:2.8.9")

    implementation("com.airbnb.android:lottie-compose:6.1.0")

    implementation("com.google.code.gson:gson:2.10.1")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.32.0")

    implementation(libs.androidx.biometric)

    implementation (libs.retrofit) // okHttp

    // chucker
    releaseImplementation (libs.chucker2)
    debugImplementation (libs.chucker1)

    //stetho
    implementation(libs.stetho)
    implementation(libs.stetho.okhttp3)
    implementation(libs.stetho.js.rhino)

    /*  Dagger Hilt  */
    androidTestImplementation(libs.androidx.rules)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

}