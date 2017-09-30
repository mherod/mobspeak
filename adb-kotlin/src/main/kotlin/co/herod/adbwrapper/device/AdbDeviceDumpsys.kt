@file:Suppress("unused")

package co.herod.adbwrapper.device

import co.herod.adbwrapper.dumpsys
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.DumpsysEntry
import co.herod.adbwrapper.model.DumpsysKey
import co.herod.adbwrapper.props.processDumpsys
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class AdbDeviceDumpsys(val adbDevice: AdbDevice)

fun AdbDevice.dumpsys() = AdbDeviceDumpsys(this)

@JvmOverloads
fun AdbDeviceDumpsys.dump(dumpsysKey: DumpsysKey, args: String = ""): Observable<DumpsysEntry> = with(adbDevice) {
    Observable.timer(250, TimeUnit.MILLISECONDS)
            .flatMap { dumpsys("$dumpsysKey $args".trim()) }
            .processDumpsys("=")
            .toObservable()
            .flatMapIterable { it.entries }
            .map { DumpsysEntry(it) }
}

@JvmOverloads
fun AdbDeviceDumpsys.window(args: String = ""): Observable<DumpsysEntry> =
        dump(DumpsysKey.WINDOW, args)

@JvmOverloads
fun AdbDeviceDumpsys.display(args: String = ""): Observable<DumpsysEntry> =
        dump(DumpsysKey.DISPLAY, args)

@JvmOverloads
fun AdbDeviceDumpsys.inputMethod(args: String = ""): Observable<DumpsysEntry> =
        dump(DumpsysKey.INPUT_METHOD, args)
