package co.herod.adbwrapper;

import co.herod.adbwrapper.model.AdbDevice;

import java.util.concurrent.TimeUnit;

/**
 * Created by matthewherod on 23/04/2017.
 */
public class Playground {

    public static void main(final String[] args) {

        AdbStreams.streamAdbCommands().subscribe(System.out::println);

        final AdbDevice connectedAdbDevice = AdbDeviceManager.getConnectedDevice();

        AdbDeviceActions.turnDeviceScreenOn(connectedAdbDevice);

        // TODO UiHierarchyBus
        AdbUi.streamUiNodeStrings(connectedAdbDevice)
                // .filter(s -> nodeTextContains(s, "0%"))
                // .compose(new FixedDurationTransformer(10, TimeUnit.SECONDS))
                .debounce(1, TimeUnit.SECONDS)
                // .distinct()
                .doOnNext(System.out::println)
                .doOnNext(s -> ScreenshotHelper.screenshotUiNode(connectedAdbDevice, s))
                .doOnNext(s -> AdbDeviceActions.tapUiNode(connectedAdbDevice, s))
                .doOnNext(s -> AdbDeviceActions.tapCoords(connectedAdbDevice, 80, 100))
                .onErrorReturn(a -> "")
                .blockingSubscribe();
    }

}
