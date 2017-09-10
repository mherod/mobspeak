package co.herod.adbwrapper

import co.herod.adbwrapper.AdbProcesses.dumpsys
import co.herod.adbwrapper.AdbProcesses.pressKey
import co.herod.adbwrapper.AdbProcesses.readDeviceFile
import co.herod.adbwrapper.AdbProcesses.uiautomatorDump
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.AdbUiHierarchy
import co.herod.adbwrapper.model.AdbUiNode
import co.herod.adbwrapper.util.UiHierarchyHelper
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by matthewherod on 23/04/2017.
 */
object Adb {

    const val DEVICES = "devices"

    fun typeText(adbDevice: AdbDevice, inputText: String) =
            AdbProcesses.inputText(adbDevice, inputText)

    fun pressKeyBlocking(adbDevice: AdbDevice, key: Int): String =
            pressKey(adbDevice, key)
                    .timeout(10, TimeUnit.SECONDS)
                    .blockingLast("")

    fun getDisplayDumpsys(adbDevice: AdbDevice): Observable<Map<String, String>> =
            adbDevice.dumpsysMap(AdbDeviceProperties.PROPS_DISPLAY).toObservable()

    fun getInputMethodDumpsys(adbDevice: AdbDevice): Observable<Map<String, String>> =
            adbDevice.dumpsysMap(AdbDeviceProperties.PROPS_INPUT_METHOD).toObservable()

    fun getPackageDumpsys(adbDevice: AdbDevice, packageName: String): Observable<Map<String, String>> =
            adbDevice.dumpsysMap("package $packageName").toObservable()

    fun getActivityDumpsys(adbDevice: AdbDevice): Observable<Map<String, String>> =
            adbDevice.dumpsysMap("activity").toObservable()

    fun getActivitiesDumpsys(adbDevice: AdbDevice): Observable<Map<String, String>> =
            adbDevice.dumpsysMap("activity activities").toObservable()

    fun getWindowsDumpsys(adbDevice: AdbDevice): Observable<Map<String, String>> =
            adbDevice.dumpsysMap("\"window windows").toObservable()

    fun getWindowFocusDumpsys(adbDevice: AdbDevice): Observable<Map<String, String>> =
            adbDevice.dumpsysMap(
                    "\"window windows",
                    "grep -E 'mCurrentFocus|mFocusedApp'"
            ).toObservable()

    private fun AdbDevice.dumpsysMap(type: String): Single<Map<String, String>> =
            dumpsys(this, type).processDumpsys("=")

    private fun AdbDevice.dumpsysMap(type: String, pipe: String): Single<Map<String, String>> =
            dumpsys(this, type, pipe).processDumpsys("=")

    fun dumpUiNodes(adbDevice: AdbDevice): Observable<AdbUiNode> =
            dumpUiHierarchy(adbDevice)
                    .map { AdbUiHierarchy(it, adbDevice).xmlString }
                    .compose { UiHierarchyHelper.uiXmlToNodes(it) }
                    .map { AdbUiNode(it) }
                    .filter { Objects.nonNull(it) }

    fun dumpUiHierarchy(adbDevice: AdbDevice): Observable<String> =
            primaryDumpUiHierarchy(adbDevice)
                    .timeout(30, TimeUnit.SECONDS)

    private fun primaryDumpUiHierarchy(adbDevice: AdbDevice): Observable<String> =
            AdbProcesses.uiautomatorDumpExecOutObservable(adbDevice)
                    .filter { "<?xml" in it }
                    .timeout(5, TimeUnit.SECONDS)
                    .retry()
                    .timeout(10, TimeUnit.SECONDS)
                    .onErrorResumeNext(fallbackDumpUiHierarchy(adbDevice))

    private fun fallbackDumpUiHierarchy(adbDevice: AdbDevice): Observable<String> =
            uiautomatorDump(adbDevice)
                    .map { it.split(' ').last() }
                    .filter { ".xml" in it }
                    .flatMap { readDeviceFile(adbDevice, "shell cat $it") }
                    .filter { "<?xml" in it }
                    .timeout(5, TimeUnit.SECONDS)
                    .retry()
                    .timeout(20, TimeUnit.SECONDS)

    fun command(adbDevice: AdbDevice, command: String): Observable<String> =
            AdbProcesses.adb(adbDevice, command)

    internal fun now(device: AdbDevice, command: String) {
        command(device, command).blockingSubscribe()
    }
}

private fun Observable<String>.processDumpsys(c: String): Single<Map<String, String>> =
        this.filter { c in it }
                .map { it.trim { it <= ' ' }.split(c.toRegex(), 2) }
                .toMap({ it[0].trim { it <= ' ' } }) { it[1].trim { it <= ' ' } }
