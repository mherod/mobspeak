package co.herod.adbwrapper;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Created by matthewherod on 23/04/2017.
 */
public class Playground {

    public static void main(String[] args) {

        // AdbBus.getBus().subscribe(System.out::println);

        //Adb.connectedDevices()
        //        .blockingSubscribe(Adb::pressPowerButton);

        allPropertyMap(DeviceManager.getConnectedDevice());

        Adb.connectedDevices()
                .flatMap(DeviceProperties::displayProperties)
                .map(Playground::outputEntry)
                .blockingSubscribe(System.out::println);

        Adb.connectedDevices()
                .flatMap(DeviceProperties::inputMethodProperties)
                .map(Playground::outputEntry)
                .blockingSubscribe(System.out::println);
    }

    private static Map<String, String> allPropertyMap(Device connectedDevice) {
        Map<String, String> properties = new HashMap<>();
        properties.putAll(propertyMap(connectedDevice, DeviceProperties::displayProperties));
        properties.putAll(propertyMap(connectedDevice, DeviceProperties::inputMethodProperties));
        return properties;
    }

    private static Map<String, String> propertyMap(Device device, Function<Device, ObservableSource<? extends Map.Entry<String, String>>> properties) {

        return Observable.just(device)
                .flatMap(properties)
                .toList()
                .flatMap(DataTransformers::entryListToMapSingle)
                .blockingGet();
    }

    private static String outputEntry(Map.Entry<String, String> entry) {
        return String.format("%s is %s", entry.getKey(), entry.getValue());
    }
}
