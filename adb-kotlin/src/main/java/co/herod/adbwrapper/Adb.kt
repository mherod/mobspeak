package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.AdbUiHierarchy
import co.herod.adbwrapper.model.AdbUiNode
import co.herod.adbwrapper.util.UiHierarchyHelper
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by matthewherod on 23/04/2017.
 */
object Adb {

    val processFactory: IProcessFactory = ProcessFactory()

    const val DEVICES = "devices"

    fun devices(): Observable<AdbDevice> = AdbProcesses.devicesObservable()
            .filter { "\t" in it }
            .map { AdbDevice.parseAdbString(it) }

    fun typeText(adbDevice: AdbDevice, inputText: String) =
            AdbProcesses.inputText(adbDevice, inputText)?.let {
                processFactory.blocking(it, 5, TimeUnit.SECONDS)
            }

    fun pressKeyBlocking(adbDevice: AdbDevice?, key: Int) =
            AdbProcesses.pressKey(adbDevice, key)?.let {
                processFactory.blocking(it, 5, TimeUnit.SECONDS)
            }

    fun getDisplayDumpsys(adbDevice: AdbDevice): Flowable<Map<String, String>> =
            adbDevice.dumpsysMap(AdbDeviceProperties.PROPS_DISPLAY).toFlowable()

    fun getInputMethodDumpsys(adbDevice: AdbDevice): Flowable<Map<String, String>> =
            adbDevice.dumpsysMap(AdbDeviceProperties.PROPS_INPUT_METHOD).toFlowable()

    fun getPackageDumpsys(adbDevice: AdbDevice, packageName: String): Flowable<Map<String, String>> =
            adbDevice.dumpsysMap("package $packageName").toFlowable()

    fun getActivityDumpsys(adbDevice: AdbDevice): Flowable<Map<String, String>> =
            adbDevice.dumpsysMap("activity").toFlowable()

    fun getActivitiesDumpsys(adbDevice: AdbDevice): Flowable<Map<String, String>> =
            adbDevice.dumpsysMap("activity activities").toFlowable()

    private fun AdbDevice.dumpsysMap(type: String): Single<Map<String, String>> =
            AdbProcesses.dumpsysObservable(this, type)
                    .filter { "=" in it }
                    .map { it.trim { it <= ' ' }.split("=".toRegex(), 2) }
                    .toMap({ it[0].trim { it <= ' ' } }) { it[1].trim { it <= ' ' } }

    fun dumpUiNodes(adbDevice: AdbDevice?): Observable<AdbUiNode> = dumpUiHierarchy(adbDevice)
            .map { AdbUiHierarchy(it, adbDevice).xmlString }
            .compose { UiHierarchyHelper.uiXmlToNodes(it) }
            .map { AdbUiNode(it) }
            .filter { Objects.nonNull(it) }

    fun dumpUiHierarchy(adbDevice: AdbDevice?): Observable<String> =
            primaryDumpUiHierarchy(adbDevice)
                    .timeout(30, TimeUnit.SECONDS)

    internal fun primaryDumpUiHierarchy(adbDevice: AdbDevice?): Observable<String> =
            AdbProcesses.uiautomatorDumpExecOutObservable(adbDevice)
                    //.doOnNext(System.out::println)
                    .filter { "<?xml" in it }
                    .timeout(10, TimeUnit.SECONDS)
                    .retry()
                    .onErrorResumeNext(fallbackDumpUiHierarchy(adbDevice))

    internal fun fallbackDumpUiHierarchy(adbDevice: AdbDevice?): Observable<String> =
            AdbProcesses.uiautomatorDumpObservable(adbDevice)
                    .map { it.split(' ').last() }
                    .filter { ".xml" in it }
                    .flatMap { AdbProcesses.readDeviceFileObservable(adbDevice, it) }
                    .filter { "<?xml" in it }
                    // .doOnNext(System.out::println)
                    .timeout(10, TimeUnit.SECONDS)
                    .retry()
    //.onErrorResumeNext(primaryDumpUiHierarchy(adbDevice))

    fun command(adbDevice: AdbDevice?, command: String): Observable<String> =
            processFactory.observableProcess(AdbProcesses.adb(adbDevice, command))

    internal fun blocking(device: AdbDevice?, command: String) {
        processFactory.blocking(AdbProcesses.adb(device, command))
    }
}