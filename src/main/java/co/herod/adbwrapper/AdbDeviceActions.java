package co.herod.adbwrapper;

import co.herod.adbwrapper.model.AdbDevice;
import co.herod.adbwrapper.util.UiHierarchyHelper;
import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.TimeUnit;

@SuppressWarnings({"unused", "WeakerAccess"})
public class AdbDeviceActions {

    public static final int KEY_EVENT_HOME = 3;
    public static final int KEY_EVENT_BACK = 4;
    public static final int KEY_EVENT_POWER = 26;

    public static void pressHomeButton(@Nullable final AdbDevice adbDevice) {
        Adb.pressKeyBlocking(adbDevice, KEY_EVENT_HOME);
    }

    public static void pressBackButton(@Nullable final AdbDevice adbDevice) {
        Adb.pressKeyBlocking(adbDevice, KEY_EVENT_BACK);
    }

    public static void pressPowerButton(@Nullable final AdbDevice adbDevice) {
        Adb.pressKeyBlocking(adbDevice, KEY_EVENT_POWER);
    }

    public static void turnDeviceScreenOn(@NotNull final AdbDevice adbDevice) {
        while (!AdbDeviceProperties.isScreenOn(adbDevice)) pressPowerButton(adbDevice);
    }

    public static void turnDeviceScreenOff(@NotNull final AdbDevice adbDevice) {
        while (AdbDeviceProperties.isScreenOn(adbDevice)) pressPowerButton(adbDevice);
    }

    public static void tapCentre(final AdbDevice connectedAdbDevice, final Integer[] c) {

        Completable.fromObservable(ProcessHelper.observableProcess(AdbProcesses.tap(connectedAdbDevice, UiHierarchyHelper.centreX(c), UiHierarchyHelper.centreY(c))))
                .blockingAwait(5, TimeUnit.SECONDS);
    }

    @NotNull
    public static Disposable tapUiNode(final AdbDevice connectedAdbDevice, @NotNull final String s) {
        return UiHierarchyHelper.extractBoundsInts(s).subscribe(c -> tapCentre(connectedAdbDevice, c));
    }

    public static boolean tapCoords(final AdbDevice connectedAdbDevice, final int x, final int y) {

        return Completable.fromObservable(ProcessHelper.observableProcess(AdbProcesses.tap(connectedAdbDevice, x, y)))
                .blockingAwait(5, TimeUnit.SECONDS);
    }
}
