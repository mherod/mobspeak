package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice

fun AdbDevice.execute(command: String, silent: Boolean = false) {
    command(command).blockingSubscribe({
        if (it.isNotBlank() && silent.not()) {
            println("Discarded output: $it")
        }
    }, {
        println("Error: $it")
    })
}