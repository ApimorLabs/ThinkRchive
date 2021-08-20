package work.racka.thinkrchive.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import work.racka.thinkrchive.data.model.Thinkpad

suspend fun List<Thinkpad>.getChipNamesList(): List<String> {
    return withContext(Dispatchers.Default) {
        val collection = mutableSetOf<String>()
        this@getChipNamesList.forEach { thinkpad ->
            collection.add(thinkpad.series)
        }
        collection.toList()
    }
}

// An empty Thinkpad entry
fun emptyThinkpad(): Thinkpad = Thinkpad(
    model = "",
    imageUrl = "",
    releaseDate = "",
    series = "",
    marketPriceStart = 0,
    marketPriceEnd = 0,
    processorPlatforms = "",
    processors = "",
    graphics = "",
    maxRam = "",
    displayRes = "",
    touchScreen = "",
    screenSize = "",
    backlitKb = "",
    fingerPrintReader = "",
    kbType = "",
    dualBatt = "",
    internalBatt = "",
    externalBatt = "",
    psrefLink = "",
    biosVersion = "",
    knownIssues = "",
    knownIssuesLinks = "",
    displaysSupported = "",
    otherMods = "",
    otherModsLinks = "",
    biosLockIn = "",
    ports = ""
)

/**
 * DOES NOT WORK DUE TO LONG STRING JSON OF Thinkpad OBJECT
 *
 * Helper functions to pass Parcelable objects into navigation argument:
 * As of compose-navigation-2.4.0-alpha06 there is no way to pass a Parcelable or
 * Serializable object in the navArgument since it will just implicitly cast it to a
 * String and will cause a crash when navigating with IllegalArgumentException.
 * This work around converts the object to a json string with YourType.toJsonString() which
 * can be passed as a string navArgument. Then you can rebuild this string on the other side
 * of navigation with String.fromJsonString() which will build your object.
 *
 * These can be called inside inside your composable or ViewModel.
 * To see their usage take a look at ThinkrchiveNavHost.kt and ThinkpadDetailsViewModel.kt
 *
 * This workaround is limited with the Json String length you can pass as an argument
 * inside where NavController won't be able to recognize your destination and end up
 * throwing an Exception.
 * For a workaround to this issue you can visit: https://stackoverflow.com/a/68009752/15285215
 *
 **/
/*
fun Thinkpad.toJsonString(): String {
    val moshiAdapter = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
        .adapter(Any::class.java)
        .lenient()
    return moshiAdapter.toJson(this)
}

fun String.fromJsonString(): Thinkpad? {
    val moshiAdapter = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
        .adapter(Thinkpad::class.java)
        .lenient()
    return moshiAdapter.fromJson(this)
}
 */
