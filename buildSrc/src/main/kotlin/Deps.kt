
object Deps {

    // Essentials
    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.coreKtx}" }
    val appCompat by lazy { "androidx.appcompat:appcompat:${Versions.appCompat}" }
    val material by lazy { "com.google.android.material:material:${Versions.material}" }

    // Lifecycle components
    val lifecycleRuntimeKtx by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtx}" }

    // Testing
    val junit by lazy { "junit:junit:${Versions.junit}" }
    val junitTest by lazy { "androidx.test.ext:junit:${Versions.junitTest}" }
    val espressoCore by lazy { "androidx.test.espresso:espresso-core:${Versions.espressoTest}" }

    // Compose Testing
    val junitCompose by lazy { "androidx.compose.ui:ui-test-junit4:${Versions.compose}" }
    val composeTooling by lazy { "androidx.compose.ui:ui-tooling:${Versions.compose}" }

    // Compose
    val composeUi by lazy { "androidx.compose.ui:ui:${Versions.compose}" }
    val composeMaterial by lazy { "androidx.compose.material:material:${Versions.compose}" }
    val composeAnimation by lazy { "androidx.compose.animation:animation:${Versions.compose}" }
    val composePreview by lazy { "androidx.compose.ui:ui-tooling-preview:${Versions.compose}" }
    val composeActivity by lazy { "androidx.activity:activity-compose:${Versions.activityCompose}" }
    val composeViewModel by lazy { "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycleViewModelCompose}" }
    val composeNavigation by lazy { "androidx.navigation:navigation-compose:${Versions.composeNavigation}" }
    val composeMaterialIconsCore by lazy { "androidx.compose.material:material-icons-core:${Versions.compose}" }
    val composeMaterialIconsExtended by lazy { "androidx.compose.material:material-icons-extended:${Versions.compose}" }
    val composeFoundation by lazy { "androidx.compose.foundation:foundation:${Versions.compose}" }
    val composeFoundationLayout by lazy { "androidx.compose.foundation:foundation-layout:${Versions.compose}" }
    val composeConstraintLayout by lazy { "androidx.constraintlayout:constraintlayout-compose:${Versions.composeConstraintLayout}" }

    // Networking & JSON
    val retrofit by lazy { "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}" }
    val retrosheet by lazy { "com.github.theapache64:retrosheet:${Versions.retrosheet}" }
    val moshi by lazy { "com.squareup.moshi:moshi:${Versions.moshi}" }
    val moshiKotlin by lazy { "com.squareup.moshi:moshi-kotlin:${Versions.moshi}" }

    // Timber Logging
    val timber by lazy { "com.jakewharton.timber:timber:${Versions.timber}" }

    // Dagger Hilt
    val hilt by lazy { "com.google.dagger:hilt-android:${Versions.hilt}" }
    val hiltNavigationCompose by lazy { "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}" }
    val hiltCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.hilt}" }

    // Coil image loader
    val coilImage by lazy { "io.coil-kt:coil-compose:${Versions.coilImage}" }

    // Accompanist
    val accompanistInsets by lazy { "com.google.accompanist:accompanist-insets:${Versions.accompanist}" }
    val accompanistNavigationAnimations by lazy { "com.google.accompanist:accompanist-navigation-animation:${Versions.accompanist}" }

    // Room
    val roomRuntime by lazy { "androidx.room:room-runtime:${Versions.room}" }
    val roomCompiler by lazy { "androidx.room:room-compiler:${Versions.room}" }
    val roomKtx by lazy { "androidx.room:room-ktx:${Versions.room}" }
    val roomTest by lazy { "androidx.room:room-testing:${Versions.room}" }

}