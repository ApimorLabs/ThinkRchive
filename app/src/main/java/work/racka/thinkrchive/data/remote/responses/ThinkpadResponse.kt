package work.racka.thinkrchive.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
data class ThinkpadResponse(
    val model: String,
    val imageUrl: String,
    val releaseDate: String,
    val series: String,
    val marketPriceStart: Int,
    val marketPriceEnd: Int,
    val processorPlatforms: String,
    val processors: String,
    val graphics: String,
    val maxRam: String,
    val displayRes: String,
    val touchScreen: String,
    val screenSize: String,
    val backlitKb: String,
    val fingerPrintReader: String,
    val kbType: String,
    val dualBatt: String,
    val internalBatt: String,
    val externalBatt: String,
    val psrefLink: String,
    val biosVersion: String,
    val knownIssues: String,
    val knownIssuesLinks: String,
    val displaysSupported: String,
    val otherMods: String,
    val otherModsLinks: String,
    val biosLockIn: String,
    val ports: String
)
