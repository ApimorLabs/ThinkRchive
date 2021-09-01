
plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = AppConfig.compileSdkVersion

    defaultConfig {
        applicationId = "work.racka.thinkrchive"
        minSdk = AppConfig.minSdkVersion
        targetSdk = AppConfig.targetSdkVersion
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
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
        kotlinCompilerExtensionVersion = Versions.compose
    }
    packagingOptions {
        resources {
            excludes += mutableSetOf(
                "/META-INF/{AL2.0,LGPL2.1}"
            )
        }
    }
}

dependencies {

    implementation(Deps.coreKtx)
    implementation(Deps.appCompat)
    implementation(Deps.material)
    implementation(Deps.lifecycleRuntimeKtx)

    testImplementation(Deps.junit)
    androidTestImplementation(Deps.junitTest)
    androidTestImplementation(Deps.espressoCore)

    // Testing Compose
    androidTestImplementation(Deps.junitCompose)
    debugImplementation(Deps.composeTooling)

    // Compose
    implementation(Deps.composeUi)
    implementation(Deps.composeAnimation)
    implementation(Deps.composeMaterial)
    implementation(Deps.composePreview)
    implementation(Deps.composeActivity)
    implementation(Deps.composeViewModel)
    implementation(Deps.composeNavigation)
    implementation(Deps.composeMaterialIconsCore)
    implementation(Deps.composeMaterialIconsExtended)
    implementation(Deps.composeFoundation)
    implementation(Deps.composeFoundationLayout)
    implementation(Deps.composeConstraintLayout)

    // Retrofit
    implementation(Deps.retrofit)

    // Retrosheet
    implementation(Deps.retrosheet)

    // Moshi
    implementation(Deps.moshi)
    implementation(Deps.moshiKotlin)

    // Timber
    implementation(Deps.timber)

    // Hilt
    implementation(Deps.hilt)
    implementation(Deps.hiltNavigationCompose)
    kapt(Deps.hiltCompiler)

    // Coil Image loader
    implementation(Deps.coilImage)

    // Accompanist
    implementation(Deps.accompanistInsets)
    implementation(Deps.accompanistNavigationAnimations)

    // Room database
    implementation(Deps.roomRuntime)
    implementation(Deps.roomKtx)
    kapt(Deps.roomCompiler)

    // Room test
    testImplementation(Deps.roomTest)

    // Preferences DataStore
    implementation(Deps.prefDataStore)

    // Splash Screen
    implementation(Deps.splashScreenCore)

}