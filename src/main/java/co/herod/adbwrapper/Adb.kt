package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.util.StringUtils
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit

/**
 * Created by matthewherod on 23/04/2017.
 */
object Adb {

    const val DEVICES = "devices"

    fun devices(): Observable<AdbDevice> {

        return ProcessHelper.observableProcess(AdbProcesses.devices())
                .filter({ StringUtils.containsTab(it) })
                .map({ AdbDevice.parseAdbString(it) })
    }

    fun typeText(adbDevice: AdbDevice, inputText: String) {
        ProcessHelper.blocking(AdbProcesses.inputText(adbDevice, inputText), 5, TimeUnit.SECONDS)
    }

    fun pressKeyBlocking(adbDevice: AdbDevice, key: Int) {
        ProcessHelper.blocking(AdbProcesses.pressKey(adbDevice, key), 5, TimeUnit.SECONDS)
    }

    fun getDisplayDumpsys(adbDevice: AdbDevice): Observable<Map<String, String>> {
        return dumpsysMap(adbDevice, AdbDeviceProperties.PROPS_DISPLAY).toObservable()
    }

    fun getInputMethodDumpsys(adbDevice: AdbDevice): Observable<Map<String, String>> {
        return dumpsysMap(adbDevice, AdbDeviceProperties.PROPS_INPUT_METHOD).toObservable()
    }

    fun dumpsysMap(adbDevice: AdbDevice, type: String): Single<Map<String, String>> {

        return ProcessHelper.observableProcess(AdbProcesses.dumpsys(adbDevice, type))
                .filter({ StringUtils.containsKeyValueSeparator(it) })
                .map<Array<String>>({ StringUtils.splitKeyValue(it) })
                .toMap({ s -> s[0].trim { it <= ' ' } }) { s -> s[1].trim { it <= ' ' } }
    }

    fun dumpUiHierarchy(adbDevice: AdbDevice): Observable<String> {
        return ProcessHelper.observableProcess(AdbProcesses.dumpUiHierarchyProcess(adbDevice))
    }

    internal fun blocking(device: AdbDevice, command: String) {
        ProcessHelper.blocking(AdbProcesses.adb(device, command))
    }

    fun command(adbDevice: AdbDevice, command: String): Observable<String> {
        return ProcessHelper.observableProcess(AdbProcesses.adb(adbDevice, command))
    }
}
