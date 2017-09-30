@file:Suppress("unused")

package co.herod.adbwrapper.testing

import co.herod.adbwrapper.device.dump
import co.herod.adbwrapper.device.dumpsys
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.DumpsysEntry
import co.herod.adbwrapper.model.DumpsysKey
import io.reactivex.Observable

fun AdbDeviceTestHelper.getActivityName(): String = with(adbDevice) {

    getDumpsysWindowFocus()
            .map { extractActivityName(it) }
            .distinctUntilChanged()
            .blockingFirst()
}

private fun extractActivityName(it: DumpsysEntry) =
        it.value.substringAfterLast('.').substringBefore('}').substringBefore(' ')

private fun AdbDevice.getDumpsysWindowFocus(): Observable<DumpsysEntry> = dumpsys()
        .dump(DumpsysKey.WINDOW, "windows")
        .filter { it.key == "mFocusedApp" || it.key == "mCurrentFocus" }