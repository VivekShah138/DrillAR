plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.devtools.ksp)
}

android {
    namespace = "com.example.drillar"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.drillar"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Required for ARCore to work properly
//        ndk {
//            abiFilters += listOf("armeabi-v7a", "arm64-v8a")
//        }
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

    // View Model Library
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    //Material Icons Extended Lib
    implementation(libs.androidx.material.icons.extended)

    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.core)
    implementation(libs.koin.compose)
    implementation(libs.koin.coroutines)
    implementation(libs.koin.compose.viewmodel)
    implementation(libs.koin.annotation)
    ksp(libs.koin.ksp.compiler)

    // Navigation
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    // KSP dependency for Room compiler
    ksp(libs.androidx.room.compiler)

    implementation("io.github.sceneview:sceneview:1.3.0")
    implementation("io.github.sceneview:arsceneview:2.3.0")



//    // ARCore
//    implementation("com.google.ar:core:1.41.0")
//
//    // Sceneform (for 3D rendering in AR)
//    implementation("com.gorisse.thomas.sceneform:sceneform:1.22.0")

}