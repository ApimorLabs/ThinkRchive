package work.racka.thinkrchive.data.dataTransferObjects

import work.racka.thinkrchive.data.model.Thinkpad
import work.racka.thinkrchive.data.responses.ThinkpadResponse

// Converting data from API to a domain model that will be read by the UI
fun List<ThinkpadResponse>.asDomainModel(): List<Thinkpad> {
    return map {
        Thinkpad(
            model = it.model,
            imageUrl = it.imageUrl,
            releaseDate = it.releaseDate,
            series = it.series,
            marketPriceStart = it.marketPriceStart,
            marketPriceEnd = it.marketPriceEnd,
            processorPlatforms = it.processorPlatforms,
            processors = it.processors,
            graphics = it.graphics,
            maxRam = it.maxRam,
            displayRes = it.displayRes,
            touchScreen = it.touchScreen,
            screenSize = it.screenSize,
            backlitKb = it.backlitKb,
            fingerPrintReader = it.fingerPrintReader,
            kbType = it.kbType,
            dualBatt = it.dualBatt,
            internalBatt = it.internalBatt,
            externalBatt = it.externalBatt,
            psrefLink = it.psrefLink,
            biosVersion = it.biosVersion,
            knownIssues = it.knownIssues,
            knownIssuesLinks = it.knownIssuesLinks,
            displaysSupported = it.displaysSupported,
            otherMods = it.otherMods,
            otherModsLinks = it.otherModsLinks,
            biosLockIn = it.biosLockIn
        )
    }
}