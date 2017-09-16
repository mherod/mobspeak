package co.herod.adbwrapper.ext

import co.herod.adbwrapper.execute
import co.herod.adbwrapper.model.AdbDevice

class AdbDevicePackageManager(val adbDevice: AdbDevice)

fun AdbDevice.pm(): AdbDevicePackageManager = AdbDevicePackageManager(this)

fun AdbDevicePackageManager.forceStop(packageName: String) = with(adbDevice) {
    execute("shell am force-stop $packageName")
}

fun AdbDevicePackageManager.installPackage(apkPath: String) = with(adbDevice) {
    execute("install $apkPath")
}

fun AdbDevicePackageManager.updatePackage(apkPath: String) = with(adbDevice) {
    execute("install -r $apkPath")
}

fun AdbDevicePackageManager.uninstallPackage(packageName: String) = with(adbDevice) {
    execute( "uninstall $packageName")
}