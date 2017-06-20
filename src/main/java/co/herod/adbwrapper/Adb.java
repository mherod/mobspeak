package co.herod.adbwrapper;

import co.herod.adbwrapper.model.AdbDevice;
import co.herod.adbwrapper.util.StringUtils;
import io.reactivex.Observable;
import io.reactivex.Single;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by matthewherod on 23/04/2017.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Adb {

    public static Observable<AdbDevice> devices() {

        return ProcessHelper.observableProcess(AdbProcesses.devices())
                .filter(StringUtils::containsTab)
                .map(AdbDevice::parseAdbString);
    }

    public static void typeText(final AdbDevice adbDevice, final String inputText) {
        ProcessHelper.blocking(AdbProcesses.inputText(adbDevice, inputText), 5, TimeUnit.SECONDS);
    }

    public static void pressKeyBlocking(final AdbDevice adbDevice, final int key) {
        ProcessHelper.blocking(AdbProcesses.pressKey(adbDevice, key), 5, TimeUnit.SECONDS);
    }

    public static Observable<Map<String, String>> getDisplayDumpsys(final AdbDevice adbDevice) {
        return dumpsysMap(adbDevice, AdbDeviceProperties.PROPS_DISPLAY).toObservable();
    }

    public static Observable<Map<String, String>> getInputMethodDumpsys(final AdbDevice adbDevice) {
        return dumpsysMap(adbDevice, AdbDeviceProperties.PROPS_INPUT_METHOD).toObservable();
    }

    public static Single<Map<String, String>> dumpsysMap(final AdbDevice adbDevice, final String type) {

        return ProcessHelper.observableProcess(AdbProcesses.dumpsys(adbDevice, type))
                .filter(StringUtils::containsKeyValueSeparator)
                .map(StringUtils::splitKeyValue)
                .toMap(s -> s[0].trim(), s -> s[1].trim());
    }

    public static Observable<String> dumpUiHierarchy(final AdbDevice adbDevice) {
        return ProcessHelper.observableProcess(AdbProcesses.dumpUiHierarchyProcess(adbDevice));
    }

    static void blocking(final AdbDevice device, final String command) {
        ProcessHelper.blocking(AdbProcesses.adb(device, command));
    }

    public static Observable<String> command(final AdbDevice adbDevice, final String command) {
        return ProcessHelper.observableProcess(AdbProcesses.adb(adbDevice, command));
    }
}
