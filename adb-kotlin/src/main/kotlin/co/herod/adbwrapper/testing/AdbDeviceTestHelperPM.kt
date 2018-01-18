/*
 * Copyright (c) 2018. Herod
 */

package co.herod.adbwrapper.testing

import co.herod.adbwrapper.AdbPackageManager
import co.herod.adbwrapper.device.installPackage
import co.herod.adbwrapper.device.pm
import co.herod.adbwrapper.device.updatePackage
import co.herod.kotlin.ext.assertExists
import java.io.File

fun AdbDeviceTestHelper.installApk(apkPath: String) = with(adbDevice) {
    File(apkPath).assertExists().also { pm().installPackage(apkPath) }
}

fun AdbDeviceTestHelper.updateApk(apkPath: String) = with(adbDevice) {
    File(apkPath).assertExists().also { pm().updatePackage(apkPath) }
}

fun AdbDeviceTestHelper.getInstalledPackages(): MutableList<String> = with(adbDevice) {
    AdbPackageManager.listPackages(this)
}

fun AdbDeviceTestHelper.getPackageVersionName(packageName: String): String? = with(adbDevice) {
    AdbPackageManager.getPackageVersionName(this, packageName)
}

fun AdbDeviceTestHelper.installedPackageIsVersion(
        packageName: String,
        versionName: String
): Boolean = getPackageVersionName(packageName) == versionName