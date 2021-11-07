dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        google()
        mavenCentral()
        maven(url = "https://www.jitpack.io")
        maven(url = "https://androidx.dev/snapshots/latest/artifacts/repository")
    }
}
rootProject.name = "ThinkRchive"
include(":app")
