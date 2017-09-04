package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit

/**
 * Created by matthewherod on 23/04/2017.
 */
object Adb {

    val processFactory: IProcessFactory = ProcessFactory()

    const val DEVICES = "devices"

    fun devices(): Observable<AdbDevice> = processFactory.observableProcess(AdbProcesses.devices())
            .filter { "\t" in it }
            .map { AdbDevice.parseAdbString(it) }

    fun typeText(adbDevice: AdbDevice, inputText: String): ProcessBuilder? =
            AdbProcesses.inputText(adbDevice, inputText).also {
                it?.let { processFactory.blocking(it, 5, TimeUnit.SECONDS) }
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

    private fun AdbDevice.dumpsysMap(type: String): Single<Map<String, String>> =
            processFactory.observableProcess(AdbProcesses.dumpsys(this, type))
                    .filter { "=" in it }
                    .map { it.trim { it <= ' ' }.split("=".toRegex(), 2) }
                    .toMap({ it[0].trim { it <= ' ' } }) { it[1].trim { it <= ' ' } }

    fun dumpUiHierarchy(adbDevice: AdbDevice?): Observable<String> {
        return primaryDumpUiHierarchy(adbDevice)
                .onErrorResumeNext(fallbackDumpUiHierarchy(adbDevice)
                        .onErrorResumeNext(dumpUiHierarchy(adbDevice)))
                .retry()
                .timeout(30, TimeUnit.SECONDS)
    }

    private fun primaryDumpUiHierarchy(adbDevice: AdbDevice?) =
            processFactory.observableProcess(AdbProcesses.dumpUiHierarchyProcess(adbDevice))
                    .filter { it.contains("<?xml") }
                    .timeout(5, TimeUnit.SECONDS)

    private fun fallbackDumpUiHierarchy(adbDevice: AdbDevice?) =
            processFactory.observableProcess(AdbProcesses.uiautomatorDump(adbDevice))
                    .concatWith(processFactory.observableProcess(AdbProcesses.pullWindowDump(adbDevice)))
                    .filter { it.contains("<?xml") }
                    .timeout(10, TimeUnit.SECONDS)

    fun command(adbDevice: AdbDevice?, command: String): Observable<String> =
            processFactory.observableProcess(AdbProcesses.adb(adbDevice, command))

    internal fun blocking(device: AdbDevice?, command: String) {
        processFactory.blocking(AdbProcesses.adb(device, command))
    }
}