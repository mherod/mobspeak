package co.herod.adbwrapper;

import co.herod.adbwrapper.model.Device;

/**
 * Created by matthewherod on 23/04/2017.
 */
public class Playground {

    public static void main(String[] args) {

        AdbStreams.streamAdbCommands().subscribe(System.out::println);

        final Device connectedDevice = DeviceManager.getConnectedDevice();

        DeviceActions.turnDeviceScreenOn(connectedDevice);

        // TODO UiHierarchyBus
        Adb.subscribeUiHierarchyUpdates(connectedDevice)
                // .filter(s -> nodeTextContains(s, "0%"))
                // .compose(new FixedDurationTransformer(10, TimeUnit.SECONDS))
                // .debounce(2, TimeUnit.SECONDS)
                .distinct()
                .doOnNext(System.out::println)
                // .doOnNext(s -> tapUiNode(connectedDevice, s))
                // .doOnNext(s -> tapCoords(connectedDevice, 80, 100))
                .onErrorReturn(a -> "")
                .blockingSubscribe();
    }
}
