package co.herod.adbwrapper;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.annotations.CheckReturnValue;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by matthewherod on 23/04/2017.
 */
class Adb {

    private static final String DEVICES = "devices";

    private static final int KEY_EVENT_BACK = 4;
    private static final int KEY_EVENT_POWER = 26;

    static Observable<Device> connectedDevices() {

        return observableProcess(devices())
                .filter(s -> s.contains("\t"))
                .map(Device::parseAdbString);
    }


    static void pressPowerButtonBlocking(Device device) {
        pressPowerButton(device).blockingAwait();
    }

    @CheckReturnValue
    private static Completable pressBackButton(@Nullable final Device device) {
        return pressKey(KEY_EVENT_BACK, device);
    }

    @CheckReturnValue
    private static Completable pressPowerButton(@Nullable Device device) {
        return pressKey(KEY_EVENT_POWER, device);
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
