package work.racka.thinkrchive.data.model

import com.squareup.moshi.Json

data class Thinkpad(
    val model: String,
    val imageUrl: String,
    val date: String,
    val series: String,
    val marketPriceStart: Int,
    val marketPriceEnd: Int,
    val processorPlatforms: String,
    val processors: String,
    val graphics: String,
    val maxRam: String,
    val displayRes: String,
    val touchScreen: String,
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
    val biosLockIn: String
)
