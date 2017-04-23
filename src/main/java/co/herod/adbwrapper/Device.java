package co.herod.adbwrapper;

/**
 * Created by matthewherod on 23/04/2017.
 */
public class Device {

    public static Device parseAdbString(final String adbDeviceString) {

        final Device device = new Device();

        final String[] split = adbDeviceString.split("\t", 2);

        device.deviceName = split[0];
        device.type = split[1];

        return device;
    }

    public String deviceName;
    public String type;

    @Override
    public String toString() {
        return "Device{" +
                "deviceName='" + deviceName + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
