package co.herod.adbwrapper;

import co.herod.adbwrapper.model.AdbDevice;
import org.jetbrains.annotations.Nullable;

class AdbProcesses {

    static ProcessBuilder devices() {
        return new AdbCommand.Builder().setCommand(Adb.DEVICES).processBuilder();
    }

    static ProcessBuilder dumpsys(final AdbDevice adbDevice, final String type) {
        return adb(adbDevice, String.format(AdbCommand.SHELL + " dumpsys %s", type));
    }

    static ProcessBuilder pressKey(@Nullable final AdbDevice adbDevice, final int key) {
        return adb(adbDevice, String.format(AdbCommand.SHELL + " input keyevent %d", key));
    }

    static ProcessBuilder inputText(final AdbDevice adbDevice, final String inputText) {
        return adb(adbDevice, String.format(AdbCommand.SHELL + " input text %s", inputText));
    }

    static ProcessBuilder dumpUiHierarchyProcess(final AdbDevice adbDevice) {
        return adb(adbDevice, "exec-out uiautomator dump /dev/tty");
    }

    static ProcessBuilder tap(@Nullable final AdbDevice adbDevice, final int x, final int y) {
        return adb(adbDevice, String.format(AdbCommand.SHELL + " input tap %d %d", x, y));
    }

    static ProcessBuilder adb(final AdbDevice adbDevice, final String command) {

        return new AdbCommand.Builder()
                .setDevice(adbDevice)
                .setCommand(command)
                .processBuilder();
    }
}
