package co.herod.adbwrapper;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.Callable;

class DeviceProperties {

    static Observable<Map.Entry<String, String>> inputMethodProperties(Device device) {
        return Observable.just(device)
                .flatMap(Adb::getInputMethodDumpsys)
                .flatMapIterable(Map::entrySet)
                .filter(DeviceProperties::validProperty)
                .sorted(Comparator.comparing(Map.Entry::getKey));
    }

    static Observable<Map.Entry<String, String>> displayProperties(Device device) {
        return Observable.just(device)
                .flatMap(Adb::getDisplayDumpsys)
                .flatMapIterable(Map::entrySet)
                .filter(DeviceProperties::validProperty)
                .sorted(Comparator.comparing(Map.Entry::getKey));
    }

    private static boolean validProperty(Map.Entry<String, String> a) {
        return !a.getKey().contains(" ");
    }
}