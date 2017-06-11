package co.herod.adbwrapper;

import co.herod.adbwrapper.model.AdbDevice;

/**
 * Created by matthewherod on 23/04/2017.
 */
public class Playground {

    public static void main(final String[] args) {

        AdbStreams.streamAdbCommands().subscribe(System.out::println);

        final AdbDevice connectedAdbDevice = AdbDeviceManager.getConnectedDevice();

        AdbDeviceActions.turnDeviceScreenOn(connectedAdbDevice);

        // TODO UiHierarchyBus
        AdbUi.streamUiHierarchyUpdates(connectedAdbDevice)
                // .filter(s -> nodeTextContains(s, "0%"))
                // .compose(new FixedDurationTransformer(10, TimeUnit.SECONDS))
                // .debounce(2, TimeUnit.SECONDS)
                // .distinct()
                .doOnNext(System.out::println)
                .doOnNext(s -> ScreenshotHelper.screenshotUiNode(connectedAdbDevice, s))
                // .doOnNext(s -> tapUiNode(connectedAdbDevice, s))
                // .doOnNext(s -> tapCoords(connectedAdbDevice, 80, 100))
                .onErrorReturn(a -> "")
                .blockingSubscribe();
    }
}
