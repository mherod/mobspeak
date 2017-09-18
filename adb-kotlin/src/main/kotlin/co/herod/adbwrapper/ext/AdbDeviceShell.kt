@file:Suppress("unused")

package co.herod.adbwrapper.ext

import co.herod.adbwrapper.AdbCommand
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.observable
import io.reactivex.Observable

class AdbDeviceShell(val adbDevice: AdbDevice)

fun AdbDevice.shell(): AdbDeviceShell = AdbDeviceShell(this)

fun AdbDeviceShell.logcat(): Observable<String> =
        AdbCommand.Builder()
                .setDevice(adbDevice)
                .setCommand("shell")
                .observable()