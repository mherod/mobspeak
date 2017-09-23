package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.AdbUiHierarchy
import co.herod.adbwrapper.model.UiNode
import co.herod.adbwrapper.util.UiHelper
import co.herod.kotlin.ext.blocking
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by matthewherod on 23/04/2017.
 */
object Adb {

    fun getPackageDumpsys(adbDevice: AdbDevice, packageName: String = ""): Observable<Map<String, String>> =
            dumpsys(adbDevice, "package $packageName".trim())
                    .processDumpsys("=")
                    .toObservable()

//    fun getActivityDumpsys(adbDevice: AdbDevice) =
//            dumpsys(adbDevice, "activity")
//                    .processDumpsys("=")
//                    .toObservable()
//
//    fun getActivitiesDumpsys(adbDevice: AdbDevice) =
//            dumpsys(adbDevice, "activity activities")
//                    .processDumpsys("=")
//                    .toObservable()

//    fun AdbDevice.getWindowFocusDumpsys() =
//            dumpsys().windows().filterKeys("mCurrentFocus", "mFocusedApp")

//    private fun AdbDevice.dumpsysMap(type: String, pipe: String): Single<Map<String, String>> =
//            dumpsys(this, type, pipe).processDumpsys("=")


    fun dumpUiNodes(
            adbDevice: AdbDevice,
            timeout: Long = 30,
            timeUnit: TimeUnit = TimeUnit.SECONDS
    ): Observable<UiNode> =
            dumpUiHierarchy(adbDevice, timeout, timeUnit)
                    .distinct { it }
                    .map { AdbUiHierarchy(it, adbDevice).xmlString }
                    .compose { UiHelper.uiXmlToNodes(it) }
                    .filter { Objects.nonNull(it) }

    fun dumpUiHierarchy(
            adbDevice: AdbDevice,
            timeout: Long = 30,
            timeUnit: TimeUnit = TimeUnit.SECONDS
    ): Observable<String> = Observable.just(adbDevice)
            .flatMap {
                when {
                    it.preferredUiAutomatorStrategy == 0 ->
                        adbDevice.compatDumpUiHierarchy()
                    it.preferredUiAutomatorStrategy == 1 ->
                        adbDevice.primaryDumpUiHierarchy(10, TimeUnit.SECONDS)
                    it.preferredUiAutomatorStrategy == 2 ->
                        adbDevice.fallbackDumpUiHierarchy(10, TimeUnit.SECONDS)
                    else ->
                        adbDevice.compatDumpUiHierarchy(10, TimeUnit.SECONDS)
                }
            }
            .onErrorResumeNext(adbDevice.fallbackDumpUiHierarchy(10, TimeUnit.SECONDS))
            .timeout(timeout, timeUnit)
            .map { it.substring(it.indexOf('<'), it.lastIndexOf('>') + 1) }


    private fun AdbDevice.compatDumpUiHierarchy(
            timeout: Long = 30,
            timeUnit: TimeUnit = TimeUnit.SECONDS
    ): Observable<String> =
            uiautomatorDumpAndRead(this)
                    .filter { it.isXmlOutput() }
                    .doOnNext { run { preferredUiAutomatorStrategy = 0 } }
                    .timeout(maxOf(5, timeout / 3), timeUnit)
                    .retry()
                    .timeout(timeout, timeUnit)

    private fun AdbDevice.primaryDumpUiHierarchy(
            timeout: Long = 30,
            timeUnit: TimeUnit = TimeUnit.SECONDS
    ): Observable<String> =
            uiautomatorDumpExecOut(this)
                    .filter { it.isXmlOutput() }
                    .doOnNext { run { preferredUiAutomatorStrategy = 1 } }
                    .timeout(maxOf(5, timeout / 3), timeUnit)
                    .retry()
                    .timeout(timeout, timeUnit)

    private fun AdbDevice.fallbackDumpUiHierarchy(
            timeout: Long = 30,
            timeUnit: TimeUnit = TimeUnit.SECONDS
    ): Observable<String> =
            uiautomatorDump(this)
                    .map { it.split(' ').last() }
                    .filter { ".xml" in it }
                    .startWith("/sdcard/window_dump.xml")
                    .flatMap {
                        readDeviceFile(this, "shell cat $it")
                                .doOnNext { run { preferredUiAutomatorStrategy = 2 } }
                                .retry()
                                .timeout(maxOf(5, timeout / 3), timeUnit)
                    }
                    .filter { it.isXmlOutput() }
                    .timeout(timeout, timeUnit)
                    .retry()
                    .timeout(30, timeUnit)

}

fun AdbDevice.command(command: String): Observable<String> =
        AdbCommand.Builder()
                .setDevice(this)
                .setCommand(command)
                .observable()

fun AdbDevice.execute(command: String) {
    command(command).blocking()
}

private fun String.isXmlOutput() = "<?xml" in this

fun Observable<String>.processDumpsys(c: String): Single<Map<String, String>> =
        this
                // .doOnNext(System.out::println)
                .filter { c in it }
                .map { it.trim() }
                .map {
                    it.split(
                            regex = c.toRegex(),
                            limit = 2
                    )
                }
                .toMap({ it[0].trim() }) { it[1].trim() }
