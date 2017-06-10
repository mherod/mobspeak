package co.herod.adbwrapper.model;

/**
 * Created by matthewherod on 23/04/2017.
 */
public class Device {

    private static final String DEVICE_CONNECTED_DEVICE = "device";
    private static final String DEVICE_EMULATOR = "emulator";

    private String deviceIdentifier;
    private String type;

    static Device parseAdbString(final String adbDeviceString) {

        final Device device = new Device();

        final String[] split = adbDeviceString.split("\t", 2);

        device.deviceIdentifier = split[0];
        device.type = split[1];

        return device;
    }

    public String getDeviceIdentifier() {
        return deviceIdentifier;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {

        return "Device{" +
                "deviceIdentifier='" + deviceIdentifier + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    static boolean isEmulator(Device device) {
        return device.getType().equals(DEVICE_EMULATOR);
    }

    static boolean isConnectedDevice(Device device) {
        return device.getType().equals(DEVICE_CONNECTED_DEVICE);
    }
}
