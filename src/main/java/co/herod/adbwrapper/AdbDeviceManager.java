package co.herod.adbwrapper;

import co.herod.adbwrapper.model.AdbDevice;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.CheckReturnValue;

import java.util.List;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("WeakerAccess")
public class AdbDeviceManager {

    @CheckReturnValue
    public static AdbDevice getConnectedDevice() {
        return getConnectedDeviceSingle().blockingGet();
    }

    @CheckReturnValue
    public static Single<AdbDevice> getConnectedDeviceSingle() {
        return connectedDevices()
                .filter(AdbDevice::isConnectedDevice)
                .firstOrError()
                .retryWhen(handler -> handler.delay(1, TimeUnit.SECONDS))
                .timeout(10, TimeUnit.SECONDS);
    }

    @CheckReturnValue
    public static List<AdbDevice> getAllDevices() {
        return connectedDevices().toList().blockingGet();
    }

    private static Observable<AdbDevice> connectedDevices() {
        return Adb.devices();
    }
}
