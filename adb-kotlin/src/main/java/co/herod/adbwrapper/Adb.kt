package co.herod.adbwrapper

import co.herod.adbwrapper.device.dump
import co.herod.adbwrapper.device.dumpsys
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.DumpsysKey
import co.herod.adbwrapper.props.processDumpsys
import co.herod.kotlin.ext.filterKeys
import io.reactivex.Observable
import io.reactivex.Single


fun getActivityDumpsys(adbDevice: AdbDevice) =
        adbDevice.dumpsys("activity")
                .processDumpsys("=")
                .toObservable()

fun getActivitiesDumpsys(adbDevice: AdbDevice) =
        adbDevice.dumpsys("activity activities")
                .processDumpsys("=")
                .toObservable()

fun AdbDevice.getWindowFocusDumpsys() = dumpsys()
                .dump(dumpsysKey = DumpsysKey.WINDOW, args = "windows")
                .filterKeys("mCurrentFocus", "mFocusedApp")

private fun AdbDevice.dumpsysMap(type: String, pipe: String): Single<Map<String, String>> =
        this.dumpsys(type, pipe).processDumpsys("=")


fun AdbDevice.command(command: String): Observable<String> =
        AdbCommand.Builder()
                .setDevice(this)
                .setCommand(command)
                .observable()

fun AdbDevice.execute(command: String, silent: Boolean = false) {
    command(command).blockingSubscribe {
        if (it.isNotBlank() && silent.not()) {
            println("Discarded output: $it")
        }
    }
}

