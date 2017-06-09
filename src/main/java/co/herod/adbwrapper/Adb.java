package co.herod.adbwrapper;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by matthewherod on 23/04/2017.
 */
@SuppressWarnings("WeakerAccess")
class Adb {

    private static final String DEVICES = "devices";

    public static final int KEY_EVENT_HOME = 3;
    public static final int KEY_EVENT_BACK = 4;
    public static final int KEY_EVENT_POWER = 26;

    private static final String DISPLAY = "display";
    private static final String INPUT_METHOD = "input_method";

    static Observable<Device> connectedDevices() {

        return observableProcess(devices())
                .filter(s -> s.contains("\t"))
                .map(Device::parseAdbString);
    }

    private static Single<Map<String, String>> dumpsysMap(Device device, String type) {

        return observableProcess(dumpsys(device, type))
                .filter(s -> s.contains("="))
                .map(s -> s.trim().split("=", 2))
                .toMap(s -> s[0].trim(), s -> s[1].trim());
    }

    private static ProcessBuilder dumpsys(Device device, String type) {

        return new AdbCommand.Builder()
                .setDevice(device)
                .setCommand(String.format("shell dumpsys %s", type))
                .processBuilder();
    }

    static void tapBlocking(Device device, final int x, final int y) {

        Completable.fromObservable(observableProcess(tap(device, x, y)))
                .blockingAwait(5, TimeUnit.SECONDS);
    }

    static void pressKeyBlocking(Device device, final int key) {

        Completable.fromObservable(observableProcess(pressKey(device, key)))
                .blockingAwait(5, TimeUnit.SECONDS);
    }

    private static ProcessBuilder tap(@Nullable Device device, final int x, final int y) {
        return new AdbCommand.Builder()
                .setDevice(device)
                .setCommand(String.format("shell input tap %d %d", x, y))
                .processBuilder();
    }

    private static ProcessBuilder pressKey(@Nullable Device device, final int key) {
        return new AdbCommand.Builder()
                .setDevice(device)
                .setCommand(String.format("shell input keyevent %d", key))
                .processBuilder();
    }

    private static ProcessBuilder devices() {
        return new AdbCommand.Builder()
                .setCommand(DEVICES)
                .processBuilder();
    }

    private static ProcessBuilder dumpUiHierarchyProcess(Device device) {
        return new AdbCommand.Builder()
                .setDevice(device)
                .setCommand("exec-out uiautomator dump /dev/tty")
                .processBuilder();
    }

    @SuppressWarnings("SameParameterValue")
    static Observable<String> dumpUiHierarchy(Device connectedDevice, String packageIdentifier) {

        return dumpUiHierarchy(connectedDevice)
                .filter(s -> s.contains("package=\"" + packageIdentifier + "\""));
    }

    static Observable<String> dumpUiHierarchy(Device device) {

        return observableProcess(dumpUiHierarchyProcess(device))
                .map(s -> s.substring(s.indexOf('<'), (s.lastIndexOf('>') + 1)))
                .flatMapIterable(s -> Arrays.asList(s.split(">")))
                .map(s -> {
                    s = s.trim();
                    if (!s.endsWith(">")) s += ">";
                    return s;
                })
                .filter(s -> s.contains("="))
                .filter(s -> s.contains(UiHierarchyHelper.KEY_STRING_BOUNDS))
                .retry()
                .onErrorReturn(throwable -> {
                    throwable.printStackTrace();
                    return "";
                });
    }

    private static Observable<String> observableProcess(final ProcessBuilder processBuilder) {
        return Observable.just(processBuilder)
                .map(ProcessBuilder::start)
                .flatMap(Adb::observableProcess)
                .doOnEach(AdbBus.getBus());
    }

    private static Observable<String> observableProcess(final Process process) {

        return Observable.fromPublisher(s -> {

            String line;

            final InputStream inputStream = process.getInputStream();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                while ((line = br.readLine()) != null) {
                    s.onNext(line);
                }
            } catch (IOException e) {
                s.onError(e);
            }
            s.onComplete();
        });
    }

    static Observable<Map<String, String>> getDisplayDumpsys(Device device) {
        return dumpsysMap(device, DISPLAY).toObservable();
    }

    static Observable<Map<String, String>> getInputMethodDumpsys(Device device) {
        return dumpsysMap(device, INPUT_METHOD).toObservable();
    }

    static boolean adbFilter(String s) {
        return s.startsWith("adb ") && !s.trim().equals("Killed");
    }

    static Observable<Integer[]> extractBoundsInts(String s) {
        return Observable.just(s)
                .map(UiHierarchyHelper::extractBounds)
                .map(s1 -> s1.split(","))
                .map(Utils::stringArrayToIntArray);
    }

    static Observable<String> subscribeUiHierarchyUpdates(Device connectedDevice) {

        return dumpUiHierarchy(connectedDevice)
                .repeat()
                .filter(Utils::isNotEmpty)
                .distinct();
    }

    static Observable<String> subscribeUiHierarchyUpdates(Device connectedDevice, String packageIdentifier) {

        return dumpUiHierarchy(connectedDevice, packageIdentifier)
                .repeat()
                .filter(Utils::isNotEmpty)
                .distinct();
    }
}
