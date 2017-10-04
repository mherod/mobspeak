package co.herod.adbwrapper.props

import co.herod.adbwrapper.device.dump
import co.herod.adbwrapper.device.dumpsys
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.DumpsysKey
import co.herod.adbwrapper.model.UiBounds
import co.herod.adbwrapper.model.filterProperty
import co.herod.kotlin.ext.containsAll
import co.herod.kotlin.ext.crop
import co.herod.kotlin.ext.toIntArray

internal fun AdbDevice.lookupWindowBounds(): UiBounds = dumpsys()
        .dump(dumpsysKey = DumpsysKey.WINDOW)
        .filterProperty("mBounds")
        .map { it.value }
        .filter { it.containsAll('[', ']') }
        .map { it.crop('[', ']') }
        .map { it.split(',') }
        .map { it -> it.toIntArray() }
        .map { UiBounds(it) }
        .blockingGet()

