package co.herod.adbwrapper;

import co.herod.adbwrapper.model.Device;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.CheckReturnValue;

import java.util.List;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("WeakerAccess")
public class DeviceManager {

    @CheckReturnValue
    public static Device getConnectedDevice() {
        return getConnectedDeviceSingle().blockingGet();
    }

    @CheckReturnValue
    public static Single<Device> getConnectedDeviceSingle() {
        return connectedDevices()
                .filter(Device::isConnectedDevice)
                .firstOrError()
                .retryWhen(handler -> handler.delay(1, TimeUnit.SECONDS))
                .timeout(10, TimeUnit.SECONDS);
    }

    @CheckReturnValue
    public static List<Device> getAllDevices() {
        return connectedDevices().toList().blockingGet();
    }

    private static Observable<Device> connectedDevices() {
        return Adb.connectedDevices();
    }
}
