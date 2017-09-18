package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import java.util.*

object AdbPackageManager {

    fun launchApp(adbDevice: AdbDevice, packageName: String) =
            adbDevice.execute("shell monkey -p $packageName 1")

    fun listPackages(adbDevice: AdbDevice): MutableList<String> =
            adbDevice.command("shell pm list packages")
                    .filter { ":" in it }
                    .map { it.split(":").last() }
                    .toSortedList()
                    .onErrorReturn { Collections.emptyList() }
                    .blockingGet()

    fun getPackageVersionName(adbDevice: AdbDevice, packageName: String): String? =
            Adb.getPackageDumpsys(adbDevice, packageName)
                    .map { it["versionName"] }
                    .blockingFirst()
}

