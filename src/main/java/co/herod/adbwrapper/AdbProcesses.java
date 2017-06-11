package co.herod.adbwrapper;

import co.herod.adbwrapper.model.AdbDevice;
import org.jetbrains.annotations.Nullable;

class AdbProcesses {

    private static final String DEVICES = "devices";

    static ProcessBuilder devices() {
        return new AdbCommand.Builder().setCommand(DEVICES).processBuilder();
    }

    static ProcessBuilder dumpsys(final AdbDevice adbDevice, final String type) {
        return adb(adbDevice, String.format("shell dumpsys %s", type));
    }

    static ProcessBuilder pressKey(@Nullable final AdbDevice adbDevice, final int key) {
        return adb(adbDevice, String.format("shell input keyevent %d", key));
    }

    static ProcessBuilder dumpUiHierarchyProcess(final AdbDevice adbDevice) {
        return adb(adbDevice, "exec-out uiautomator dump /dev/tty");
    }

    static ProcessBuilder screenCaptureTake(final AdbDevice adbDevice) {
        return adb(adbDevice, "shell screencap -p /sdcard/screen.png");
    }

    static ProcessBuilder screenCapturePull(final AdbDevice adbDevice) {
        return adb(adbDevice, "pull /sdcard/screen.png");
    }

    static ProcessBuilder tap(@Nullable final AdbDevice adbDevice, final int x, final int y) {
        return adb(adbDevice, String.format("shell input tap %d %d", x, y));
    }

    static ProcessBuilder adb(final AdbDevice adbDevice, final String command) {

        return new AdbCommand.Builder()
                .setDevice(adbDevice)
                .setCommand(command)
                .processBuilder();
    }
}
