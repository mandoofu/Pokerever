plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    id("kotlin-kapt") // kapt 플러그인 추가
    id("dagger.hilt.android.plugin") // Hilt 플러그인 추가
}


android {
    namespace = "com.mandoo.pokerever"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mandoo.pokerever"
        minSdk = 24
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
    // 기본 의존성
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.compose)
    implementation(libs.kotlinx.collections.immutable)

    // Firebase 관련 의존성
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.messaging.ktx.v2331)
    implementation(libs.firebase.database.ktx.v2030)
    implementation(libs.firebase.bom)
    implementation(libs.firebase.firestore.ktx)

    // Hilt 관련 의존성
    implementation(libs.hilt.android.v245)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.storage.ktx)  // 최신 버전
    kapt(libs.hilt.android.compiler)

    // ViewModel, Compose용 Hilt
    implementation(libs.androidx.hilt.navigation.compose.v100) // 정확한 버전 확인
    kapt(libs.androidx.hilt.compiler.v100)  // Hilt 컴파일러


    // 테스트 의존성
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}