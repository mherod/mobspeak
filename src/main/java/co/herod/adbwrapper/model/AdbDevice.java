package co.herod.adbwrapper.model;

import org.jetbrains.annotations.NotNull;

/**
 * Created by matthewherod on 23/04/2017.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class AdbDevice {

    private static final String DEVICE_CONNECTED_DEVICE = "device";
    private static final String DEVICE_EMULATOR = "emulator";

    private String deviceIdentifier;
    private String type;

    @NotNull
    public static AdbDevice parseAdbString(@NotNull final String adbDeviceString) {

        final AdbDevice adbDevice = new AdbDevice();

        final String[] split = adbDeviceString.split("\t", 2);

        adbDevice.deviceIdentifier = split[0];
        adbDevice.type = split[1];

        return adbDevice;
    }

    public static boolean isEmulator(@NotNull final AdbDevice adbDevice) {
        return adbDevice.getType().equals(DEVICE_EMULATOR);
    }

    public static boolean isConnectedDevice(@NotNull final AdbDevice adbDevice) {
        return adbDevice.getType().equals(DEVICE_CONNECTED_DEVICE);
    }

    public String getDeviceIdentifier() {
        return deviceIdentifier;
    }

    public String getType() {
        return type;
    }

    @NotNull
    @Override
    public String toString() {

        return "AdbDevice{" +
                "deviceIdentifier='" + deviceIdentifier + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
