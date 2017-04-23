package co.herod.adbwrapper;

/**
 * Created by matthewherod on 23/04/2017.
 */
public class Device {

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
}
