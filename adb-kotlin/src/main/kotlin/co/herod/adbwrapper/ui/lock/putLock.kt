package co.herod.adbwrapper.ui.lock

import co.herod.adbwrapper.execute
import co.herod.adbwrapper.model.AdbDevice

fun AdbDevice.putLock() {
    // TODO identify something
    execute("shell echo $s1 > $lockPath")
}