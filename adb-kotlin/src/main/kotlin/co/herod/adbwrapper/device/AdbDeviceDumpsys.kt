@file:Suppress("unused")

package co.herod.adbwrapper.device

import co.herod.adbwrapper.dumpsys
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.DumpsysEntry
import co.herod.adbwrapper.model.DumpsysKey
import co.herod.adbwrapper.processDumpsys
import io.reactivex.Observable

class AdbDeviceDumpsys(val adbDevice: AdbDevice)

fun AdbDevice.dumpsys() = AdbDeviceDumpsys(this)

@JvmOverloads
fun AdbDeviceDumpsys.dump(dumpsysKey: DumpsysKey, args: String = ""): Observable<DumpsysEntry> = with(adbDevice) {
    dumpsys(this, "$dumpsysKey $args".trim())
            .processDumpsys("=")
            .toObservable()
            .flatMapIterable { it.entries }
            .map { DumpsysEntry(it) }
}

@JvmOverloads
@Deprecated(
        replaceWith = ReplaceWith("dump(dumpsysKey = DumpsysKey.WINDOW)"),
        message = "Use dump(dumpsysKey, args)"
)
fun AdbDeviceDumpsys.window(args: String = ""): Observable<DumpsysEntry> =
        dump(DumpsysKey.WINDOW, args)

@JvmOverloads
@Deprecated(
        replaceWith = ReplaceWith("dump(dumpsysKey = DumpsysKey.DISPLAY)"),
        message = "Use dump(dumpsysKey, args)"
)
fun AdbDeviceDumpsys.display(args: String = ""): Observable<DumpsysEntry> =
        dump(DumpsysKey.DISPLAY, args)

@JvmOverloads
@Deprecated(
        replaceWith = ReplaceWith("dump(dumpsysKey = DumpsysKey.INPUT_METHOD)"),
        message = "Use dump(dumpsysKey, args)"
)
fun AdbDeviceDumpsys.inputMethod(args: String = ""): Observable<DumpsysEntry> =
        dump(DumpsysKey.INPUT_METHOD, args)
