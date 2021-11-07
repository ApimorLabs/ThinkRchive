
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
        viewBinding = true
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
    testImplementation(Deps.testArchCore)
    testImplementation(Deps.junitTest)
    testImplementation(Deps.testExtJUnitKtx)
    testImplementation(Deps.mockitoInline)
    testImplementation(Deps.mockitoKotlin)
    testImplementation(Deps.robolectric)
    testImplementation(Deps.turbine)
    testImplementation(Deps.coroutineTest)

    androidTestImplementation(Deps.junitTest)
    androidTestImplementation(Deps.espressoCore)
    androidTestImplementation(Deps.testCoreKtx)
    androidTestImplementation(Deps.testArchCore)
    androidTestImplementation(Deps.mockitoAndroid)
    androidTestImplementation(Deps.mockitoKotlin)
    androidTestImplementation(Deps.turbine)

    // Hilt Testing
    // Local Unit Tests
    testImplementation(Deps.hiltTest)
    kaptTest(Deps.hiltCompiler)
    // Instrumentation Test
    androidTestImplementation(Deps.hiltTest)
    kaptAndroidTest(Deps.hiltCompiler)

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
    implementation(Deps.composeMaterial3)

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

    // Billing
    implementation(Deps.googleBilling)

    // Glance AppWidget - Early Snapshot
    implementation(Deps.glanceAppWidget)

}