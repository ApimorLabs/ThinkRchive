
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

        testInstrumentationRunner = "work.racka.thinkrchive.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
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
        kotlinCompilerExtensionVersion = Versions.compose
    }
    packagingOptions {
        resources {
            excludes += mutableSetOf(
                "/META-INF/{AL2.0,LGPL2.1}",
                "META-INF/licenses/ASM"
            )
            // Fixes conflicts caused by ByteBuddy library used in
            // coroutines-debug and mockito
            pickFirsts += mutableSetOf(
                "win32-x86-64/attach_hotspot_windows.dll",
                "win32-x86/attach_hotspot_windows.dll"
            )
        }
    }
}

dependencies {

    implementation(Deps.coreKtx)
    implementation(Deps.appCompat)
    implementation(Deps.material)
    implementation(Deps.lifecycleRuntimeKtx)

    // Testing
    testImplementation(Deps.junit)
    androidTestImplementation(Deps.junitTest)
    androidTestImplementation(Deps.espressoCore)
    androidTestImplementation(Deps.testCore)
    testImplementation("org.mockito:mockito-inline:3.8.0")
    androidTestImplementation("org.mockito:mockito-android:3.8.0")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    androidTestImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    testImplementation("org.robolectric:robolectric:4.3.1")
    testImplementation("app.cash.turbine:turbine:0.6.1")
    androidTestImplementation("app.cash.turbine:turbine:0.6.1")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")

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

    // Hilt Testing
    // Instrumentation Test
    androidTestImplementation(Deps.hiltTest)
    kaptAndroidTest(Deps.hiltCompiler)
    // Local Unit Tests
    testImplementation(Deps.hiltTest)
    kaptTest(Deps.hiltCompiler)

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

    // Billing
    implementation(Deps.googleBilling)

}