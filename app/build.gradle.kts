plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // id("com.google.gms.google-services") // firebase authentication 미사용, firestore 미구현에 따른 비활성화 진행, google-servies.json 삭제된 상태임
}


android {
    namespace = "com.example.ecoworld"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.ecoworld"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "GEMINI_API_KEY",
            "\"${project.findProperty("GEMINI_API_KEY")}\""
        )
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("C:/Users/user/.android/debug.keystore") // 본인 PC 경로 확인
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
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
        debug {
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = false // ✅ 비활성화
        viewBinding = true // ✅ 활성화
        buildConfig = true // ✅ 활성화
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.constraintlayout)  // ✅ ConstraintLayout 추가
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // ✅ Firebase BOM 및 Auth 추가
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))

    // ✅ Gemini API 호출을 위한 네트워크 라이브러리
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
    implementation(libs.material)
    implementation(libs.androidx.activity)

    // 테스트
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
