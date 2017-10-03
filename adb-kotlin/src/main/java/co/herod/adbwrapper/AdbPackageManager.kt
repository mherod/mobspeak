package co.herod.adbwrapper

import co.herod.adbwrapper.S.Companion.INTENT_CATEGORY_LAUNCHER
import co.herod.adbwrapper.S.Companion.SHELL
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.props.getPackageDumpsys
import co.herod.kotlin.ext.containsIgnoreCase
import java.util.*

object AdbPackageManager {

    fun launchApp(adbDevice: AdbDevice, packageName: String, launcher: Boolean = true): String {

        val paramPackage = "-p $packageName"
        val paramCommand = if (launcher) "-c $INTENT_CATEGORY_LAUNCHER" else ""

        val command = "$SHELL monkey $paramPackage $paramCommand 1"

        return adbDevice.command(command)
                .filter { it.containsIgnoreCase("Events injected: 1") }
                .blockingFirst()
    }

    fun listPackages(adbDevice: AdbDevice): MutableList<String> =
            adbDevice.command("$SHELL pm list packages")
                    .filter { ":" in it }
                    .map { it.split(":").last() }
                    .toSortedList()
                    .onErrorReturn { Collections.emptyList() }
                    .blockingGet()

    fun getPackageVersionName(adbDevice: AdbDevice, packageName: String): String? =
            getPackageDumpsys(adbDevice, packageName)
                    .map { it["versionName"] }
                    .map { println("Package: $packageName is version $it"); it }
                    .blockingFirst()
}

