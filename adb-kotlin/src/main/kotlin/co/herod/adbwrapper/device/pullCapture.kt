/*
 * Copyright (c) 2018. Herod
 */

package co.herod.adbwrapper.device

import co.herod.adbwrapper.command
import co.herod.adbwrapper.execute
import co.herod.adbwrapper.model.AdbDevice
import java.io.File

fun AdbDevice.pullCapture(file: File) {

    val filePath = file.absolutePath

//    if (filePath.contains(' ')) {
//        filePath = "'$filePath'"
//    }

    println(filePath)

    val devicePath = "/sdcard/screen.png"

    execute("shell screencap -p $devicePath")

    command("pull $devicePath $filePath")
            .filter { it.contains(devicePath) }
            .blockingLast()
            .let { println(it) }

    execute("shell rm $devicePath")
}