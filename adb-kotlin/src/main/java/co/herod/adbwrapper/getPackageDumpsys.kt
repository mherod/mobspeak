package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import io.reactivex.Observable

fun getPackageDumpsys(adbDevice: AdbDevice, packageName: String = ""): Observable<Map<String, String>> =
        adbDevice.dumpsys("package $packageName".trim())
                .processDumpsys("=")
                .toObservable()