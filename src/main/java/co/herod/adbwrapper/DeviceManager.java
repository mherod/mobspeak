package co.herod.adbwrapper;

import io.reactivex.Flowable;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.functions.Function;
import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

public class DeviceManager {

    @CheckReturnValue
    static Device getConnectedDevice() {

        return Adb.connectedDevices()
                .filter(DeviceManager::isConnectedDevice)
                .singleOrError()
                .retryWhen(handler -> handler.delay(1, TimeUnit.SECONDS))
                .timeout(10, TimeUnit.SECONDS)
                .blockingGet();
    }

    private static boolean isConnectedDevice(Device device) {
        return device.getType().equals("device");
    }
}
