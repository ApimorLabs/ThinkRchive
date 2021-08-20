package work.racka.thinkrchive.data.responses

import com.squareup.moshi.Json

data class ThinkpadResponse(
    val model: String,
    @Json(name = "image_url")
    val imageUrl: String,
    @Json(name = "release_date")
    val releaseDate: String,
    val series: String,
    @Json(name = "market_price_start")
    val marketPriceStart: Int,
    @Json(name = "market_price_end")
    val marketPriceEnd: Int,
    @Json(name = "processor_platforms")
    val processorPlatforms: String,
    val processors: String,
    val graphics: String,
    @Json(name = "max_ram")
    val maxRam: String,
    @Json(name = "display_res")
    val displayRes: String,
    @Json(name = "touch_screen")
    val touchScreen: String,
    @Json(name = "screen_size")
    val screenSize: String,
    @Json(name = "backlit_kb")
    val backlitKb: String,
    @Json(name = "fingerprint_reader")
    val fingerPrintReader: String,
    @Json(name = "kb_type")
    val kbType: String,
    @Json(name = "dual_batt")
    val dualBatt: String,
    @Json(name = "internal_batt")
    val internalBatt: String,
    @Json(name = "external_batt")
    val externalBatt: String,
    @Json(name = "psref_link")
    val psrefLink: String,
    @Json(name = "bios_version")
    val biosVersion: String,
    @Json(name = "known_issues")
    val knownIssues: String,
    @Json(name = "known_issues_links")
    val knownIssuesLinks: String,
    @Json(name = "displays_supported")
    val displaysSupported: String,
    @Json(name = "other_mods")
    val otherMods: String,
    @Json(name = "other_mods_links")
    val otherModsLinks: String,
    @Json(name = "bios_lock_in")
    val biosLockIn: String,
    val ports: String
)
