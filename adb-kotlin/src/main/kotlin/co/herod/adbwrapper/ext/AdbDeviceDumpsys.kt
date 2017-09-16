package co.herod.adbwrapper.ext

import co.herod.adbwrapper.Adb
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.DumpsysEntry
import io.reactivex.Observable

class AdbDeviceDumpsys(val adbDevice: AdbDevice)

fun AdbDevice.dumpsys() = AdbDeviceDumpsys(this)

fun AdbDeviceDumpsys.display(): Observable<DumpsysEntry> = with(adbDevice) {

    return Adb.getDisplayDumpsys(this)
            .flatMapIterable { it.entries }
            .filter { " " in it.key }
            .sorted(java.util.Comparator.comparing<Map.Entry<String, String>, String> { it.key })
            .map { co.herod.adbwrapper.model.DumpsysEntry(it) }
}

fun AdbDeviceDumpsys.inputMethod(): Observable<DumpsysEntry> = with(adbDevice) {

    return co.herod.adbwrapper.Adb.getInputMethodDumpsys(this)
            .flatMapIterable({ it.entries })
            .filter { " " in it.key }
            .sorted(java.util.Comparator.comparing<Map.Entry<String, String>, String> { it.key })
            .map { co.herod.adbwrapper.model.DumpsysEntry(it) }
}