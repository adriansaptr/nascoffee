// File: app/build.gradle.kts (Module: app)
// Ini adalah versi yang sudah diperbaiki untuk format Kotlin Script (.kts)

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // Plugin ksp untuk Room (database)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.nascoffee3" // Pastikan ini sesuai dengan package name Anda
    compileSdk = 34 // Menggunakan SDK yang stabil

    defaultConfig {
        applicationId = "com.mahasiswa.nascoffee3"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false // Sintaks Kotlin menggunakan isMinifyEnabled
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    // Mengaktifkan ViewBinding untuk mengakses layout XML dengan mudah dari kode Kotlin
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Dependensi dasar untuk aplikasi Android dengan Kotlin
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Dependensi untuk Splash Screen API yang modern
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Dependensi untuk Material Design (tombol, kartu, input text yang stylish)
    implementation("com.google.android.material:material:1.12.0")

    // Dependensi Jetpack Navigation (untuk perpindahan antar layar/fragment)
    val nav_version = "2.7.7"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    // Dependensi Jetpack ViewModel & LiveData (untuk arsitektur MVVM)
    val lifecycle_version = "2.8.1"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")

    // Dependensi Room Database (untuk database lokal SQLite)
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version") // Dukungan Coroutines
    ksp("androidx.room:room-compiler:$room_version") // Prosesor anotasi untuk Room

    // Dependensi Kotlin Coroutines (untuk tugas background)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Dependensi untuk testing (biarkan default)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
