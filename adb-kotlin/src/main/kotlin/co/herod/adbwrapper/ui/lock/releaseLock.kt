package co.herod.adbwrapper.ui.lock

import co.herod.adbwrapper.execute
import co.herod.adbwrapper.model.AdbDevice

fun AdbDevice.releaseLock() {
    execute("shell rm $lockPath")
}