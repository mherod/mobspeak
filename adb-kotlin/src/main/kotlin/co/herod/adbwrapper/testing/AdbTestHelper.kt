@file:JvmName("AdbTestHelperKt")
@file:Suppress("unused")

package co.herod.adbwrapper.testing

import co.herod.adbwrapper.AdbPackageManager
import co.herod.adbwrapper.device.pm
import co.herod.adbwrapper.device.pressKey
import co.herod.adbwrapper.device.typeText
import co.herod.adbwrapper.device.uninstallPackage
import co.herod.adbwrapper.launchUrl
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.screenshot
import co.herod.adbwrapper.ui.streamUi
import co.herod.kotlin.ext.blockingSilent

class AdbDeviceTestHelper(val adbDevice: AdbDevice)

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

fun AdbDevice.testHelper() = AdbDeviceTestHelper(this)

fun AdbDeviceTestHelper.startUiBus(): Boolean = with(adbDevice) {
    disposables.add(streamUi().subscribe())
}

fun AdbDeviceTestHelper.stopUiBus() = with(adbDevice) {
    dispose()
}

fun AdbDeviceTestHelper.assertPower(minPower: Int) {
    with(adbDevice) {
        TODO("not implemented: assertion for power $minPower")
    }
}

fun AdbDeviceTestHelper.backButton() = with(adbDevice) { pressKey().back() }

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

fun AdbDeviceTestHelper.takeScreenshot() = with(adbDevice) {
    screenshot(false)
}

fun AdbDeviceTestHelper.typeText(text: String) = adbDevice.typeText(text)

fun AdbDeviceTestHelper.uninstallPackage(packageName: String) = with(adbDevice) {

    pressKey().home() // go home first because input method can cause crash

    pm().uninstallPackage(packageName)
}
