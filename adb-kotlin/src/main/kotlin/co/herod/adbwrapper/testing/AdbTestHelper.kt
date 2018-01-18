/*
 * Copyright (c) 2018. Herod
 */

@file:JvmName("AdbTestHelperKt")
@file:Suppress("unused")

package co.herod.adbwrapper.testing

import co.herod.adbwrapper.AdbPackageManager
import co.herod.adbwrapper.device.input.home
import co.herod.adbwrapper.device.input.pressKey
import co.herod.adbwrapper.device.pm
import co.herod.adbwrapper.device.uninstallPackage
import co.herod.adbwrapper.launchUrl
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.screenshot
import co.herod.kotlin.ext.blockingSilent
import java.io.File

class AdbDeviceTestHelper(val adbDevice: AdbDevice)

fun AdbDevice.testHelper() = AdbDeviceTestHelper(this)

// ifUiNodeExists
// whileUiNodeExists
// untilUiNodeExists
// whileUiHierarchy
// untilUiHierarchy
// while
// until
// failIf
// untilUiNodeExists / scrollUp
// whileUiNodeExists / scrollUp

fun AdbDeviceTestHelper.assertPower(minPower: Int) {
    with(adbDevice) {
        TODO("not implemented: assertion for power $minPower")
    }
}

fun AdbDeviceTestHelper.dismissDialog() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

fun AdbDeviceTestHelper.launchApp(packageName: String) = with(adbDevice) {
    AdbPackageManager.launchApp(this, packageName)
}

fun AdbDeviceTestHelper.launchUrl(url: String) = with(adbDevice) {
    launchUrl(this, url).blockingSilent()
}

fun AdbDeviceTestHelper.launchUrl(url: String, packageName: String) = with(adbDevice) {
    launchUrl(this, url, packageName).blockingSilent()
}

fun AdbDeviceTestHelper.takeScreenshot(): File = with(adbDevice) {
    screenshot()
}

fun AdbDeviceTestHelper.uninstallPackage(packageName: String) = with(adbDevice) {

    pressKey().home() // go home first because input method can cause crash

    pm().uninstallPackage(packageName)
}
