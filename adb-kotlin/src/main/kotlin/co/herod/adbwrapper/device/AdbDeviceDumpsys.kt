@file:Suppress("unused")

package co.herod.adbwrapper.device

import co.herod.adbwrapper.dumpsys
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.DumpsysEntry
import co.herod.adbwrapper.processDumpsys
import io.reactivex.Observable

class AdbDeviceDumpsys(val adbDevice: AdbDevice)

fun AdbDevice.dumpsys() = AdbDeviceDumpsys(this)

@JvmOverloads
fun AdbDeviceDumpsys.windows(args: String = ""): Observable<DumpsysEntry> = with(adbDevice) {
    dumpsys(this, "window $args".trim())
            .processDumpsys("=")
            .toObservable()
            .flatMapIterable { it.entries }
            .map { DumpsysEntry(it) }
}

@JvmOverloads
fun AdbDeviceDumpsys.display(args: String = ""): Observable<DumpsysEntry> = with(adbDevice) {
    dumpsys(this, "display $args".trim())
            .processDumpsys("=")
            .toObservable()
            .flatMapIterable { it.entries }
            .map { DumpsysEntry(it) }
}

@JvmOverloads
fun AdbDeviceDumpsys.inputMethod(args: String = ""): Observable<DumpsysEntry> = with(adbDevice) {
    dumpsys(this, "input_method $args".trim())
            .processDumpsys("=")
            .toObservable()
            .flatMapIterable({ it.entries })
            .map { DumpsysEntry(it) }
}
