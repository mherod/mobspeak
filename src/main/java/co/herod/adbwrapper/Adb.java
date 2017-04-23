package co.herod.adbwrapper;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created by matthewherod on 23/04/2017.
 */
class Adb {

    private static final String DEVICES = "devices";

    private static final int KEY_EVENT_HOME = 3;
    private static final int KEY_EVENT_BACK = 4;
    public static final int KEY_EVENT_POWER = 26;

    public static final String DISPLAY = "display";
    public static final String INPUT_METHOD = "input_method";

    static Observable<Device> connectedDevices() {

        return observableProcess(devices())
                .filter(s -> s.contains("\t"))
                .map(Device::parseAdbString);
    }

    static Single<Map<String, String>> dumpsysMap(Device device, String type) {

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

    static void pressKeyBlocking(Device device, final int key) {
        pressKey(key, device).blockingAwait();
    }

    private static Completable pressKey(int keyEvent, @Nullable final Device device) {
        return Completable.fromObservable(observableProcess(pressKey(device, keyEvent)));
    }

    private static ProcessBuilder devices() {
        return new AdbCommand.Builder()
                .setCommand(DEVICES)
                .processBuilder();
    }

    private static ProcessBuilder pressKey(@Nullable Device device, final int key) {
        return new AdbCommand.Builder()
                .setDevice(device)
                .setCommand(String.format("shell input keyevent %d", key))
                .processBuilder();
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

    static void pressHomeButton(@Nullable Device device) {
        pressKeyBlocking(device, KEY_EVENT_HOME);
    }

    static void pressBackButton(@Nullable Device device) {
        pressKeyBlocking(device, KEY_EVENT_BACK);
    }

    static void pressPowerButton(@Nullable Device device) {
        pressKeyBlocking(device, KEY_EVENT_POWER);
    }

    static Observable<Map<String, String>> getDisplayDumpsys(Device device) {
        return dumpsysMap(device, DISPLAY).toObservable();
    }

    static Observable<Map<String, String>> getInputMethodDumpsys(Device device) {
        return dumpsysMap(device, INPUT_METHOD).toObservable();
    }
}
