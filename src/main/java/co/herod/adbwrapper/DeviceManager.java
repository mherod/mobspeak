package co.herod.adbwrapper;

import co.herod.adbwrapper.model.Device;
import io.reactivex.annotations.CheckReturnValue;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class DeviceManager {

    private static Device device;

    public static void selectDevice(Device device) {
        DeviceManager.device = device;
    }

    public static Device getDevice() {
        return device;
    }

    @CheckReturnValue
    static Device getConnectedDevice() {

        return Adb.connectedDevices()
                .filter(Device::isConnectedDevice)
                .firstOrError()
                .retryWhen(handler -> handler.delay(1, TimeUnit.SECONDS))
                .timeout(10, TimeUnit.SECONDS)
                .blockingGet();
    }

    static List<Device> getAllDevices() {
        return Adb.connectedDevices().toList().blockingGet();
    }

    static void selectFirstConnectedDevice() {
        selectDevice(getConnectedDevice());
    }
}
