package co.herod.adbwrapper;

import co.herod.adbwrapper.model.Device;
import co.herod.adbwrapper.rx.FixedDurationTransformer;
import co.herod.adbwrapper.rx.ResultChangeFixedDurationTransformer;
import co.herod.adbwrapper.util.StringUtils;
import co.herod.adbwrapper.util.UiHierarchyHelper;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by matthewherod on 23/04/2017.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
class Adb {

    private static final String DEVICES = "devices";

    public static final int KEY_EVENT_HOME = 3;
    public static final int KEY_EVENT_BACK = 4;
    public static final int KEY_EVENT_POWER = 26;

    private static final String DISPLAY = "display";
    private static final String INPUT_METHOD = "input_method";

    static Observable<Device> connectedDevices() {

        return ProcessHelper.observableProcess(devices())
                .filter(StringUtils::containsTab)
                .map(Device::parseAdbString);
    }

    private static Single<Map<String, String>> dumpsysMap(Device device, String type) {

        return ProcessHelper.observableProcess(dumpsys(device, type))
                .filter(StringUtils::containsKeyValueSeparator)
                .map(StringUtils::splitKeyValue)
                .toMap(s -> s[0].trim(), s -> s[1].trim());
    }

    static void pressKeyBlocking(Device device, final int key) {

        Completable.fromObservable(ProcessHelper.observableProcess(pressKey(device, key)))
                .blockingAwait(5, TimeUnit.SECONDS);
    }

    private static ProcessBuilder dumpsys(Device device, String type) {
        return adb(device, String.format("shell dumpsys %s", type));
    }

    private static ProcessBuilder pressKey(@Nullable Device device, final int key) {
        return adb(device, String.format("shell input keyevent %d", key));
    }

    private static ProcessBuilder dumpUiHierarchyProcess(Device device) {
        return adb(device, "exec-out uiautomator dump /dev/tty");
    }

    private static ProcessBuilder devices() {
        return new AdbCommand.Builder().setCommand(DEVICES).processBuilder();
    }

    @SuppressWarnings("SameParameterValue")
    static Observable<String> dumpUiHierarchy(Device connectedDevice, final String packageIdentifier) {

        return dumpUiHierarchy(connectedDevice)
                .filter(s -> s.contains("package=\"" + packageIdentifier + "\""));
    }

    static Observable<String> dumpUiHierarchy(Device device) {

        return ProcessHelper.observableProcess(dumpUiHierarchyProcess(device))
                .map(StringUtils::extractXmlString)
                .compose(new ResultChangeFixedDurationTransformer())
                .doOnNext(System.out::println)
                .compose(UiHierarchyHelper::uiXmlToNodes)
                .compose(new FixedDurationTransformer(1, TimeUnit.DAYS))
                .onErrorReturn(throwable -> {
                    throwable.printStackTrace();
                    return "";
                });
    }

    static Observable<Map<String, String>> getDisplayDumpsys(Device device) {
        return dumpsysMap(device, DISPLAY).toObservable();
    }

    static Observable<Map<String, String>> getInputMethodDumpsys(Device device) {
        return dumpsysMap(device, INPUT_METHOD).toObservable();
    }

    static Observable<Integer[]> extractBoundsInts(String s) {

        return Observable.just(s)
                .map(UiHierarchyHelper::extractBounds)
                .map(StringUtils::splitCsv)
                .map(StringUtils::stringArrayToIntArray);
    }

    static Observable<String> subscribeUiHierarchyUpdates(Device connectedDevice) {

        return dumpUiHierarchy(connectedDevice)
                .repeat()
                .filter(StringUtils::isNotEmpty);
    }

    static Observable<String> subscribeUiHierarchyUpdates(Device connectedDevice, String packageIdentifier) {

        return dumpUiHierarchy(connectedDevice, packageIdentifier)
                .repeat()
                .filter(StringUtils::isNotEmpty);
    }

    static ProcessBuilder tap(@Nullable Device device, final int x, final int y) {
        return adb(device, String.format("shell input tap %d %d", x, y));
    }

    private static ProcessBuilder adb(Device device, String command) {

        return new AdbCommand.Builder()
                .setDevice(device)
                .setCommand(command)
                .processBuilder();
    }
}
