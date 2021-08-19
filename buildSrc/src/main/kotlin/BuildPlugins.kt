
object BuildPlugins {
    val androidGradle by lazy { "com.android.tools.build:gradle:${Versions.gradlePlugin}" }
    val kotlinGradle by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}" }
    val daggerHilt by lazy { "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}" }
}