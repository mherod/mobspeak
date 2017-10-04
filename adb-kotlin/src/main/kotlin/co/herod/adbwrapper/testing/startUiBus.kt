package co.herod.adbwrapper.testing

import co.herod.adbwrapper.ui.streamUi
import co.herod.adbwrapper.uiautomator.uiAutomatorBridge

fun AdbDeviceTestHelper.startUiBus(): Boolean = with(adbDevice) {
    disposables.add(uiAutomatorBridge().subscribe())
    disposables.add(streamUi().subscribe())
}