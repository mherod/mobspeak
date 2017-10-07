package co.herod.adbwrapper.device.shell

import co.herod.adbwrapper.AdbCommand
import co.herod.adbwrapper.S
import co.herod.adbwrapper.observable
import io.reactivex.Observable

fun AdbDeviceShell.logcat(): Observable<String> =
        AdbCommand.Builder()
                .setDevice(adbDevice)
                .setCommand("${S.SHELL} logcat")
                .observable()