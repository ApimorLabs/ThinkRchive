package work.racka.thinkrchive.data.dataTransferObjects

import work.racka.thinkrchive.data.database.ThinkpadDatabaseObject
import work.racka.thinkrchive.data.model.Thinkpad
import work.racka.thinkrchive.data.responses.ThinkpadResponse

// Converting data from API to a domain model that will be read by the UI
fun List<ThinkpadResponse>.asDatabaseModel(): Array<ThinkpadDatabaseObject> {
    return map {
        ThinkpadDatabaseObject(
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
            biosLockIn = it.biosLockIn,
            ports = it.ports
        )
    }.toTypedArray()
}

// Converts database objects to domain model object that will be displayed on the UI
fun List<ThinkpadDatabaseObject>.asDomainModel(): List<Thinkpad> {
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
            biosLockIn = it.biosLockIn,
            ports = it.ports
        )
    }
}

fun ThinkpadDatabaseObject.asThinkpad(): Thinkpad {
    return Thinkpad(
        model = this.model,
        imageUrl = this.imageUrl,
        releaseDate = this.releaseDate,
        series = this.series,
        marketPriceStart = this.marketPriceStart,
        marketPriceEnd = this.marketPriceEnd,
        processorPlatforms = this.processorPlatforms,
        processors = this.processors,
        graphics = this.graphics,
        maxRam = this.maxRam,
        displayRes = this.displayRes,
        touchScreen = this.touchScreen,
        screenSize = this.screenSize,
        backlitKb = this.backlitKb,
        fingerPrintReader = this.fingerPrintReader,
        kbType = this.kbType,
        dualBatt = this.dualBatt,
        internalBatt = this.internalBatt,
        externalBatt = this.externalBatt,
        psrefLink = this.psrefLink,
        biosVersion = this.biosVersion,
        knownIssues = this.knownIssues,
        knownIssuesLinks = this.knownIssuesLinks,
        displaysSupported = this.displaysSupported,
        otherMods = this.otherMods,
        otherModsLinks = this.otherModsLinks,
        biosLockIn = this.biosLockIn,
        ports = this.ports
    )
}
