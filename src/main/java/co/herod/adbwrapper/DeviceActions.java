package co.herod.adbwrapper;

import io.reactivex.disposables.Disposable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static co.herod.adbwrapper.UiHierarchyHelper.centreX;
import static co.herod.adbwrapper.UiHierarchyHelper.centreY;

@SuppressWarnings({"WeakerAccess", "unused"})
public class DeviceActions {

    static void pressHomeButton(@Nullable Device device) {
        Adb.pressKeyBlocking(device, Adb.KEY_EVENT_HOME);
    }

    static void pressBackButton(@Nullable Device device) {
        Adb.pressKeyBlocking(device, Adb.KEY_EVENT_BACK);
    }

    static void pressPowerButton(@Nullable Device device) {
        Adb.pressKeyBlocking(device, Adb.KEY_EVENT_POWER);
    }

    static void turnDeviceScreenOn(Device device) {
        while (!DeviceProperties.isScreenOn(device)) {
            pressPowerButton(device);
        }
    }

    static void turnDeviceScreenOff(Device device) {
        while (DeviceProperties.isScreenOn(device)) {
            pressPowerButton(device);
        }
    }

    static void tapCentre(Device connectedDevice, Integer[] c) {
        Adb.tapBlocking(connectedDevice, centreX(c), centreY(c));
    }

    @NotNull
    static Disposable tapUiNode(Device connectedDevice, String s) {
        return Adb.extractBoundsInts(s)
                .subscribe(c -> tapCentre(connectedDevice, c));
    }

    static void tapCoords(Device connectedDevice, int x, int y) {
        Adb.tapBlocking(connectedDevice, x, y);
    }
}
