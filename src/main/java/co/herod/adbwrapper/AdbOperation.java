package co.herod.adbwrapper;

import org.jetbrains.annotations.Nullable;

import java.util.concurrent.TimeUnit;

/**
 * Created by matthewherod on 23/04/2017.
 */
public class AdbOperation {

    public static void main(String[] args) {

        Adb.connectedDevices()
                .debounce(3, TimeUnit.SECONDS)
                .doOnEach(deviceNotification -> pressBack(deviceNotification.getValue()).start())
                .subscribe(device -> System.out.println(device.toString()));

        // Adb.pressBack().subscribe();
    }

    private static ProcessBuilder pressBack(@Nullable Device device) {
        return new AdbCommand.Builder()
                .setDevice(device)
                .setCommand("shell input keyevent 4")
                .processBuilder();
    }
}
