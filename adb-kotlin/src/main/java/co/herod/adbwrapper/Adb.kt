package co.herod.adbwrapper

import co.herod.adbwrapper.device.dumpsys
import co.herod.adbwrapper.device.windows
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.AdbUiHierarchy
import co.herod.adbwrapper.model.UiNode
import co.herod.adbwrapper.util.UiHierarchyHelper
import co.herod.kotlin.ext.blocking
import co.herod.kotlin.ext.justKeys
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by matthewherod on 23/04/2017.
 */
object Adb {

    fun pressKeyBlocking(adbDevice: AdbDevice, key: Int): String =
            pressKey(adbDevice, key)
                    .blocking(10, TimeUnit.SECONDS)

    fun getPackageDumpsys(adbDevice: AdbDevice, packageName: String = "") =
            dumpsys(adbDevice, "package $packageName".trim())
                    .processDumpsys("=")
                    .toObservable()

    fun getActivityDumpsys(adbDevice: AdbDevice) =
            dumpsys(adbDevice, "activity")
                    .processDumpsys("=")
                    .toObservable()

    fun getActivitiesDumpsys(adbDevice: AdbDevice) =
            dumpsys(adbDevice, "activity activities")
                    .processDumpsys("=")
                    .toObservable()

    fun AdbDevice.getWindowFocusDumpsys() =
            dumpsys().windows().justKeys("mCurrentFocus", "mFocusedApp")

    private fun AdbDevice.dumpsysMap(type: String, pipe: String): Single<Map<String, String>> =
            dumpsys(this, type, pipe).processDumpsys("=")

    private val DEFAULT_TIMEOUT_SECONDS: Long = 30

    fun dumpUiNodes(adbDevice: AdbDevice): Observable<UiNode> =
            dumpUiHierarchy(
                    adbDevice,
                    DEFAULT_TIMEOUT_SECONDS,
                    TimeUnit.SECONDS
            )
                    .map { AdbUiHierarchy(it, adbDevice).xmlString }
                    .compose { UiHierarchyHelper.uiXmlToNodes(it) }
                    .map { UiNode(it) }
                    .filter { Objects.nonNull(it) }

    fun dumpUiHierarchy(
            adbDevice: AdbDevice,
            timeout: Long = DEFAULT_TIMEOUT_SECONDS,
            timeUnit: TimeUnit = TimeUnit.SECONDS
    ): Observable<String> = Observable.just(adbDevice)
            .flatMap {
                when {
                    it.preferredUiAutomatorStrategy == 0 ->
                        compatDumpUiHierarchy(adbDevice)
                    it.preferredUiAutomatorStrategy == 1 ->
                        primaryDumpUiHierarchy(adbDevice, 10, TimeUnit.SECONDS)
                    it.preferredUiAutomatorStrategy == 2 ->
                        fallbackDumpUiHierarchy(adbDevice, 10, TimeUnit.SECONDS)
                    else ->
                        compatDumpUiHierarchy(adbDevice, 10, TimeUnit.SECONDS)
                }
            }
            .onErrorResumeNext(fallbackDumpUiHierarchy(adbDevice, 10, TimeUnit.SECONDS))
            .timeout(timeout, timeUnit)

    private fun compatDumpUiHierarchy(
            adbDevice: AdbDevice,
            timeout: Long = DEFAULT_TIMEOUT_SECONDS,
            timeUnit: TimeUnit = TimeUnit.SECONDS
    ): Observable<String> =
            uiautomatorDumpAndRead(adbDevice)
                    .filter { it.isXmlOutput() }
                    .doOnNext { adbDevice.run { preferredUiAutomatorStrategy = 0 } }
                    .timeout(maxOf(5, timeout / 3), timeUnit)
                    .retry()
                    .timeout(timeout, timeUnit)

    private fun primaryDumpUiHierarchy(
            adbDevice: AdbDevice,
            timeout: Long = DEFAULT_TIMEOUT_SECONDS,
            timeUnit: TimeUnit = TimeUnit.SECONDS
    ): Observable<String> =
            uiautomatorDumpExecOut(adbDevice)
                    .filter { it.isXmlOutput() }
                    .doOnNext { adbDevice.run { preferredUiAutomatorStrategy = 1 } }
                    .timeout(maxOf(5, timeout / 3), timeUnit)
                    .retry()
                    .timeout(timeout, timeUnit)

    private fun fallbackDumpUiHierarchy(
            adbDevice: AdbDevice,
            timeout: Long = DEFAULT_TIMEOUT_SECONDS,
            timeUnit: TimeUnit = TimeUnit.SECONDS
    ): Observable<String> =
            uiautomatorDump(adbDevice)
                    .map { it.split(' ').last() }
                    .filter { ".xml" in it }
                    .startWith("/sdcard/window_dump.xml")
                    .flatMap {
                        readDeviceFile(adbDevice, "shell cat $it")
                                .doOnNext { adbDevice.run { preferredUiAutomatorStrategy = 2 } }
                                .retry()
                                .timeout(maxOf(5, timeout / 3), timeUnit)
                    }
                    .filter { it.isXmlOutput() }
                    .timeout(timeout, timeUnit)
                    .retry()
                    .timeout(DEFAULT_TIMEOUT_SECONDS, timeUnit)

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
