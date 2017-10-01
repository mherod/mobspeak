package co.herod.adbwrapper.testing

import co.herod.adbwrapper.ui.streamUi

fun AdbDeviceTestHelper.startUiBus(): Boolean = with(adbDevice) {
    disposables.add(streamUi().subscribe())
}