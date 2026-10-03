plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.wireup"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.wireup"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    implementation("androidx.compose.material:material")
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.cardview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)



    //    implementation 'androidx.compose.material:material-icons-extended'
    implementation(libs.androidx.navigation.compose)
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")
    //coil
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.28.0")
    //hilt
    implementation ("com.google.dagger:hilt-android:2.49")
    kapt ("com.google.dagger:hilt-compiler:2.49")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    implementation (libs.androidx.animation)
    implementation(libs.google.exoplayer)

    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    //noinspection UseTomlInstead
    implementation("com.google.firebase:firebase-analytics")

    implementation("com.google.accompanist:accompanist-drawablepainter:0.35.0-alpha")

    implementation(libs.google.firebase.firestore.ktx)
    implementation(libs.google.firebase.auth.ktx)
    implementation (libs.play.services.auth)

    implementation (libs.androidx.lifecycle.livedata.ktx)
    implementation (libs.firebase.storage)

    implementation (libs.androidx.palette)

    implementation (libs.core)

    implementation (libs.accompanist.pager.v0280)

    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")








}
