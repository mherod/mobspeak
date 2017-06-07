package co.herod.adbwrapper;

import io.reactivex.annotations.CheckReturnValue;

import java.util.concurrent.TimeUnit;

public class DeviceManager {

    private static Device device;

    private static boolean isConnectedDevice(Device device) {
        return device.getType().equals("device");
    }

    public static void selectDevice(Device device) {
        DeviceManager.device = device;
    }

    public static Device getDevice() {
        return device;
    }

    @CheckReturnValue
    static Device getConnectedDevice() {

        return Adb.connectedDevices()
                .filter(DeviceManager::isConnectedDevice)
                .singleOrError()
                .retryWhen(handler -> handler.delay(1, TimeUnit.SECONDS))
                .timeout(10, TimeUnit.SECONDS)
                .blockingGet();
    }

    static void selectFirstConnectedDevice() {
        selectDevice(getConnectedDevice());
    }
}
