package co.herod.adbwrapper;

/**
 * Created by matthewherod on 23/04/2017.
 */
public class Playground {

    public static void main(String[] args) {

        // AdbBus.getBus().subscribe(System.out::println);

        Adb.connectedDevices().blockingSubscribe(Adb::pressPowerButton);

        Adb.connectedDevices()
                .flatMap(Adb::getDisplayDumpsys)
                .blockingSubscribe(System.out::println);

        Adb.connectedDevices()
                .flatMap(Adb::getInputMethodDumpsys)
                .blockingSubscribe(System.out::println);
    }

}
