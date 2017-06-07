package co.herod.adbwrapper;

import io.reactivex.Observable;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

class DeviceProperties {

    private static final String M_SCREEN_STATE = "mScreenState";

    static Observable<Map.Entry<String, String>> inputMethodProperties(Device device) {
        return Observable.just(device)
                .flatMap(Adb::getInputMethodDumpsys)
                .flatMapIterable(Map::entrySet)
                .filter(PropHelper::isValidProperty)
                .sorted(Comparator.comparing(Map.Entry::getKey));
    }

    static Observable<Map.Entry<String, String>> displayProperties(Device device) {
        return Observable.just(device)
                .flatMap(Adb::getDisplayDumpsys)
                .flatMapIterable(Map::entrySet)
                .filter(PropHelper::isValidProperty)
                .sorted(Comparator.comparing(Map.Entry::getKey));
    }

    static boolean isScreenOn() {
        return isScreenOn(null);
    }

    static boolean isScreenOn(Device device) {

        return Observable.just(device, DeviceManager.getDevice())
                .filter(Objects::nonNull)
                .flatMap(DeviceProperties::displayProperties)
                .filter(entry -> PropHelper.isKey(entry, M_SCREEN_STATE))
                .map(PropHelper::hasPositiveValue)
                .firstOrError()
                .blockingGet();
    }

}