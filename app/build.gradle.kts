plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.devtools.ksp")
}


android {
    namespace = "com.example.jobfinder"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.jobfinder"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // --- Core + lifecycle ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // --- Compose BOM + UI ---
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)

    // Material 3
    implementation(libs.androidx.compose.material3)
    // You can keep this if you really need the android artifact, otherwise it’s optional:
    implementation(libs.androidx.material3.android)

    // ICONS – add BOTH core and extended (safe & simple)
    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.material3:material3:1.2.1")
    // Google Fonts
    implementation(libs.androidx.ui.text.google.fonts)

    // --- DataStore ---
    implementation(libs.datastore)
    implementation(libs.androidx.datastore.preferences.core.jvm)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // --- Networking: Retrofit + OkHttp + Kotlinx Serialization ---
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.converter)

    // --- Room (with KSP) ---
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)

    ksp(libs.androidx.room.compiler)

    // --- Coil (images) ---
    implementation(libs.coil)

    // --- Tests / debug ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
