package co.herod.adbwrapper;

/**
 * Created by matthewherod on 23/04/2017.
 */
public class Playground {

    public static void main(String[] args) {
        Adb.connectedDevices().blockingSubscribe(Adb::pressPowerButtonBlocking);
    }
}
