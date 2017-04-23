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
public class AdbOperation {

    public static void main(String[] args) {

        connectedDevices()
                .subscribe(device -> System.out.println(device.toString()));
    }

    private static Observable<Device> connectedDevices() {

        return callAdbDevices()
                .filter(AdbHelper::containsSplitValues)
                .map(Device::parseAdbString);
    }

    private static Observable<String> callAdbDevices() {
        return observableProcess(createProcess("adb", "devices"));
    }

    @NotNull
    private static ProcessBuilder createProcess(final String... strings) {

        return new ProcessBuilder(strings)
                .redirectErrorStream(true);
    }

    private static Observable<String> observableProcess(final ProcessBuilder processBuilder) {

        return Observable.just(processBuilder)
                .map(ProcessBuilder::start)
                .flatMap(AdbOperation::observableProcess);
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
