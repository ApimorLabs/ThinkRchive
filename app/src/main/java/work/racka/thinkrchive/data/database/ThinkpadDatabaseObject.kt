package work.racka.thinkrchive.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import work.racka.thinkrchive.utils.Constants.THINKPAD_LIST_TABLE

@Entity(tableName = THINKPAD_LIST_TABLE)
data class ThinkpadDatabaseObject(
    @PrimaryKey
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
