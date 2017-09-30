@file:JvmName("AdbTestHelperKt")
@file:Suppress("unused")

package co.herod.adbwrapper.testing

import co.herod.adbwrapper.AdbPackageManager
import co.herod.adbwrapper.device.*
import co.herod.adbwrapper.launchUrl
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiNode
import co.herod.adbwrapper.screenshot
import co.herod.adbwrapper.ui.streamUi

class AdbDeviceTestHelper(val adbDevice: AdbDevice)

// ifUiNodeExists
// whileUiNodeExists
// whileUiHierarchy
// untilUiHierarchy
// while
// until
// failIf

fun AdbDevice.testHelper() = AdbDeviceTestHelper(this)

fun AdbDeviceTestHelper.startUiBus(): Boolean = with(adbDevice) {
    disposables.add(streamUi().subscribe())
}

fun AdbDeviceTestHelper.stopUiBus() = with(adbDevice) {
    dispose()
}

fun AdbDeviceTestHelper.assertWithoutPackage(packageName: String) = with(adbDevice) {

    val packages = pm().installedPackages()

    if (packages.contains(packageName)) {
        throw AssertionError("Packages list contained " + packageName)
    }
}

fun AdbDeviceTestHelper.assertInstalledPackageVersionName(packageName: String, versionName: String) = with(adbDevice) {

    val packages = pm().installedPackages()

    if (!packages.contains(packageName)) {
        throw AssertionError("Packages list did not contain " + packageName)
    }

    if (!installedPackageIsVersion(packageName, versionName)) {
        throw AssertionError("Package was not correct version")
    }
}

fun AdbDeviceTestHelper.assertPower(minPower: Int) {
    with(adbDevice) {
        TODO("not implemented: assertion for power $minPower")
    }
}

fun AdbDeviceTestHelper.backButton() = with(adbDevice) { pressKey().back() }

fun AdbDeviceTestHelper.closeLeftDrawer() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

fun AdbDeviceTestHelper.dismissDialog() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

fun AdbDeviceTestHelper.launchApp(packageName: String) = with(adbDevice) {
    AdbPackageManager.launchApp(this, packageName)
}

fun AdbDeviceTestHelper.launchUrl(url: String) = with(adbDevice) {
    launchUrl(this, url).blockingSubscribe()
}

fun AdbDeviceTestHelper.launchUrl(url: String, packageName: String) = with(adbDevice) {
    launchUrl(this, url, packageName).blockingSubscribe()
}

fun AdbDeviceTestHelper.takeScreenshot() = with(adbDevice) {
    screenshot(false)
}

fun AdbDeviceTestHelper.typeText(text: String) = with(adbDevice) {
    typeText(text)
}

fun AdbDeviceTestHelper.scrollUpUntilISee(function: (UiNode) -> Boolean) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

fun AdbDeviceTestHelper.uninstallPackage(packageName: String) = with(adbDevice) {


    pressKey().home() // go home first because input method can cause crash

    pm().uninstallPackage(packageName)
}
