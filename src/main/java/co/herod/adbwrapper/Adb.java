package co.herod.adbwrapper;

import io.reactivex.Observable;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by matthewherod on 23/04/2017.
 */
class Adb {

    private static final String ADB = "adb";
    private static final String SHELL = "shell";
    private static final String DEVICES = "devices";
    private static final String INPUT = "input";
    private static final String KEYEVENT = "keyevent";

    private static final int KEY_EVENT_BACK = 4;

    @NotNull
    private static ProcessBuilder shellInputKeyEvent(int keyEvent) {

        return createProcess(ADB, SHELL, INPUT, KEYEVENT, String.valueOf(keyEvent));
    }

    @NotNull
    private static ProcessBuilder devices() {

        return createProcess(ADB, DEVICES);
    }

    @NotNull
    private static ProcessBuilder createProcess(final String... strings) {

        return new ProcessBuilder(strings)
                .redirectErrorStream(true);
    }

    static Observable<Device> connectedDevices() {

        return callAdbDevices()
                .filter(AdbHelper::containsSplitValues)
                .map(Device::parseAdbString);
    }

    static Observable<String> pressBack() {
        return callAdbInputKeyEvent(KEY_EVENT_BACK);
    }

    private static Observable<String> callAdbInputKeyEvent(int keyEvent) {
        return observableProcess(shellInputKeyEvent(keyEvent));
    }

    private static Observable<String> callAdbDevices() {
        return observableProcess(devices());
    }

    private static Observable<String> observableProcess(final ProcessBuilder processBuilder) {

        return Observable.just(processBuilder)
                .map(ProcessBuilder::start)
                .flatMap(Adb::observableProcess);
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
}
