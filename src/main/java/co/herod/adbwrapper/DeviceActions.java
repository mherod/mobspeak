package co.herod.adbwrapper;

import co.herod.adbwrapper.model.Device;
import co.herod.adbwrapper.util.UiHierarchyHelper;
import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.TimeUnit;

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
        while (!DeviceProperties.isScreenOn(device)) pressPowerButton(device);
    }

    public static void turnDeviceScreenOff(Device device) {
        while (DeviceProperties.isScreenOn(device)) pressPowerButton(device);
    }

    public static void tapCentre(Device connectedDevice, Integer[] c) {
        tapBlocking(connectedDevice, UiHierarchyHelper.centreX(c), UiHierarchyHelper.centreY(c));
    }

    @NotNull
    public static Disposable tapUiNode(Device connectedDevice, String s) {
        return Adb.extractBoundsInts(s).subscribe(c -> tapCentre(connectedDevice, c));
    }

    public static boolean tapCoords(Device connectedDevice, int x, int y) {
        return tapBlocking(connectedDevice, x, y);
    }

    public static boolean tapBlocking(Device device, final int x, final int y) {

        return Completable.fromObservable(ProcessHelper.observableProcess(Adb.tap(device, x, y)))
                .blockingAwait(5, TimeUnit.SECONDS);
    }

}
