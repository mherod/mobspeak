package co.herod.adbwrapper.testing

import co.herod.kotlin.ext.waitUntilEmpty

fun AdbDeviceTestHelper.stopUiBus() = with(adbDevice) {
    dispose()
    disposables.waitUntilEmpty()
}