package co.herod.adbwrapper.testing

fun AdbDeviceTestHelper.stopUiBus() = with(adbDevice) {
    dispose()
}