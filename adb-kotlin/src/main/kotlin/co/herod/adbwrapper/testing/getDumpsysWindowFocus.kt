package co.herod.adbwrapper.testing

import co.herod.adbwrapper.device.dump
import co.herod.adbwrapper.device.dumpsys
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.DumpsysEntry
import co.herod.adbwrapper.model.DumpsysKey
import io.reactivex.Observable

private const val FOCUSED_APP = "mFocusedApp"
private const val CURRENT_FOCUS = "mCurrentFocus"

fun AdbDevice.getDumpsysWindowFocus(): Observable<DumpsysEntry> = dumpsys()
        .dump(DumpsysKey.WINDOW, "windows")
        .filter { it.key == FOCUSED_APP || it.key == CURRENT_FOCUS }