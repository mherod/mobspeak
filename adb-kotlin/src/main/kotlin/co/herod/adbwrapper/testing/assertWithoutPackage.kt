package co.herod.adbwrapper.testing

import co.herod.adbwrapper.device.installedPackages
import co.herod.adbwrapper.device.pm

fun AdbDeviceTestHelper.assertWithoutPackage(packageName: String) = with(adbDevice) {

    val packages = pm().installedPackages()

    if (packages.contains(packageName)) {
        throw AssertionError("Packages list contained " + packageName)
    }
}