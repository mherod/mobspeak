@file:Suppress("unused")

package co.herod.adbwrapper.device

import co.herod.adbwrapper.AdbPackageManager
import co.herod.adbwrapper.execute
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.testing.AdbDeviceTestHelper

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

fun AdbDevicePackageManager.installPackage(apkPath: String) = with(adbDevice) {
    execute("install $apkPath")
    // TODO check "Success"
}

fun AdbDevicePackageManager.updatePackage(apkPath: String) = with(adbDevice) {
    execute("install -r $apkPath")
    // TODO check "Success"
}

fun AdbDevicePackageManager.uninstallPackage(packageName: String) = with(adbDevice) {
    execute("uninstall $packageName")
}

fun AdbDevicePackageManager.installedPackages(): List<String> = with(adbDevice) {
    AdbPackageManager.listPackages(this)
}
