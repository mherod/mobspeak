@file:Suppress("MemberVisibilityCanPrivate")

package co.herod.adbwrapper.model

import java.util.*

class AdbUiHierarchy(
        val xmlString: String,
        val adbDevice: AdbDevice?,
        val dumpDate: Date = Date()
) {
    override fun toString(): String {
        return "AdbUiHierarchy(xmlString='$xmlString', adbDevice=$adbDevice, dumpDate=$dumpDate)"
    }
}
