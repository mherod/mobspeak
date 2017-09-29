package co.herod.adbwrapper.props

import co.herod.adbwrapper.device.dump
import co.herod.adbwrapper.device.dumpsys
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.DumpsysKey
import co.herod.adbwrapper.model.UiBounds
import co.herod.adbwrapper.model.filterProperty

@Deprecated(
        replaceWith = ReplaceWith("windowBounds"),
        message = "Use the 'windowBounds' property"
)
internal fun AdbDevice.lookupWindowBounds(): UiBounds = dumpsys()
        .dump(dumpsysKey = DumpsysKey.WINDOW)
        .filterProperty("mBounds")
        .map { it.value }
        .filter { '[' in it && ']' in it }
        .map {
            it.substring(
                    it.lastIndexOf('[') + 1,
                    it.lastIndexOf(']')
            )
        }
        .map { it.split(',') }
        .map { it.map { Integer.parseInt(it) } }
        .map { it.toIntArray() }
        .map { UiBounds(it) }
        .blockingGet()