package co.herod.adbwrapper;

import co.herod.adbwrapper.model.Device;
import co.herod.adbwrapper.util.PropHelper;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("WeakerAccess")
class DeviceProperties {

    private static final String KEY_SCREEN_STATE = "mScreenState";

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

    static boolean isScreenOn(@NotNull Device device) {
        return isScreenOnSingle(device).blockingGet();
    }

    static Single<Boolean> isScreenOnSingle(@NotNull Device device) {
        return Observable.just(device)
                .flatMap(DeviceProperties::displayProperties)
                .filter(entry -> PropHelper.isKey(entry, KEY_SCREEN_STATE))
                .zipWith(Observable.interval(1, TimeUnit.SECONDS), (entry, a) -> entry)
                .map(PropHelper::hasPositiveValue)
                .firstOrError();
    }
}