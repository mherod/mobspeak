package co.herod.adbwrapper.testing

import co.herod.adbwrapper.device.installedPackages
import co.herod.adbwrapper.device.pm

fun AdbDeviceTestHelper.assertInstalledPackageVersionName(packageName: String, versionName: String) = with(adbDevice) {

    val packages = pm().installedPackages()

    if (!packages.contains(packageName)) {
        throw AssertionError("Packages list did not contain " + packageName)
    }

    if (!installedPackageIsVersion(packageName, versionName)) {
        throw AssertionError("Package was not correct version")
    }
}