package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import io.reactivex.Observable

fun AdbDevice.command(command: String): Observable<String> =
        AdbCommand.Builder()
                .setDevice(this)
                .setCommand(command)
                .observable()