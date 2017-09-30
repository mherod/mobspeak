@file:Suppress("unused")

package co.herod.adbwrapper.testing

import co.herod.adbwrapper.device.dump
import co.herod.adbwrapper.device.dumpsys
import co.herod.adbwrapper.model.DumpsysKey

fun AdbDeviceTestHelper.getActivityName(): String = with(adbDevice) {

    dumpsys().dump(DumpsysKey.WINDOW, "windows")
            .filter { it.key == "mFocusedApp" || it.key == "mCurrentFocus" }
            .map { it.value.substringAfterLast('.').substringBefore('}').substringBefore(' ') }
            .distinctUntilChanged()
            .blockingFirst()
}