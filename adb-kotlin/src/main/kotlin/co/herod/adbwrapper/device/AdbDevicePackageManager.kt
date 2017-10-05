@file:Suppress("unused")

package co.herod.adbwrapper.device

import co.herod.adbwrapper.AdbPackageManager
import co.herod.adbwrapper.command
import co.herod.adbwrapper.execute
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.testing.AdbDeviceTestHelper
import co.herod.kotlin.log

class AdbDevicePackageManager(val adbDevice: AdbDevice)

fun AdbDevice.pm(): AdbDevicePackageManager = AdbDevicePackageManager(this)

@Deprecated(
        replaceWith = ReplaceWith("adbDevice.pm()"),
        message = "Use adbDevice.pm()"
)
fun AdbDeviceTestHelper.pm(): AdbDevicePackageManager = adbDevice.pm()

fun AdbDevicePackageManager.forceStop(packageName: String) = with(adbDevice) {
    execute("shell am force-stop $packageName")
}

fun AdbDevicePackageManager.kill(packageName: String) = with(adbDevice) {
    execute("shell am kill $packageName")
}

fun AdbDevicePackageManager.installPackage(apkPath: String): Boolean = with(adbDevice) {
    command("install $apkPath")
            .filter { "Success" in it }
            .lastOrError()
            .doOnSuccess { log("Successfully installed $apkPath") }
            .retry()
            .map { println(it); true }
            .blockingGet()
}

fun AdbDevicePackageManager.updatePackage(apkPath: String): Boolean = with(adbDevice) {
    command("install -r $apkPath")
            .filter { "Success" in it }
            .lastOrError()
            .doOnSuccess { log("Successfully updated $apkPath") }
            .retry()
            .map { println(it); true }
            .blockingGet()
}

fun AdbDevicePackageManager.uninstallPackage(packageName: String): String = with(adbDevice) {
    command("uninstall $packageName")
            .filter { "Success" == it }
            .firstOrError()
            .doOnSuccess { log("Successfully uninstalled $packageName") }
            .retry()
            .blockingGet()
}

fun AdbDevicePackageManager.installedPackages(): List<String> = with(adbDevice) {
    AdbPackageManager.listPackages(this)
}
