package co.herod.adbwrapper;

import co.herod.adbwrapper.model.AdbDevice;
import org.jetbrains.annotations.Nullable;

import static co.herod.adbwrapper.util.Utils.enableOrDisabled;

@SuppressWarnings("unused")
public class AdbDeviceServices {

    private static final String SERVICE = "svc";

    private static final String SPACE = " ";

    public static void enableService(@Nullable final AdbDevice adbDevice, boolean enable, final String serviceType) {

        //noinspection StringBufferReplaceableByString
        Adb.INSTANCE.blocking(adbDevice, new StringBuilder()
                .append(AdbCommand.SHELL)
                .append(SPACE)
                .append(SERVICE)
                .append(SPACE)
                .append(serviceType)
                .append(SPACE)
                .append(enableOrDisabled(enable))
                .toString());
    }
}
