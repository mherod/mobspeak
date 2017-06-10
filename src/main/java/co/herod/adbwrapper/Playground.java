package co.herod.adbwrapper;

import co.herod.adbwrapper.model.Device;
import co.herod.adbwrapper.util.Utils;

/**
 * Created by matthewherod on 23/04/2017.
 */
public class Playground {

    public static void main(String[] args) {

        final Device connectedDevice = DeviceManager.getConnectedDevice();

        // use the bus to log output you're interested in
        AdbStreams.streamAdbCommands()
                .concatMap(Utils::throttleOutput)
                .doOnNext(System.out::println)
                .subscribe();

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
