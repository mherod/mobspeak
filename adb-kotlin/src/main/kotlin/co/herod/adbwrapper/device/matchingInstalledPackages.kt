package co.herod.adbwrapper.device

import co.herod.adbwrapper.model.AdbDevice

fun AdbDevice.matchingInstalledPackages(query: String = ""): List<String> =
        pm().installedPackages().filter { query in it }