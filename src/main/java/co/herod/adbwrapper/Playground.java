package co.herod.adbwrapper;

/**
 * Created by matthewherod on 23/04/2017.
 */
public class Playground {

    public static void main(String[] args) {

        final Device connectedDevice = DeviceManager.getConnectedDevice();

        // use the bus to log output you're interested in
        Adb.subscribeAdbCommands()
                .concatMap(Utils::throttleOutput)
                .doOnNext(System.out::println)
                .subscribe();

        DeviceActions.turnDeviceScreenOn(connectedDevice);

        Adb.subscribeUiHierarchyUpdates(connectedDevice)
                .doOnNext(System.out::println)
                .doOnNext(s -> DeviceActions.tapUiNode(connectedDevice, s))
                .blockingSubscribe();
    }

}
