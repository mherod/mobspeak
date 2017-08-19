package co.herod.adbwrapper;

import co.herod.adbwrapper.model.AdbDevice;
import co.herod.adbwrapper.util.PropHelper;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"WeakerAccess", "unused"})
class AdbDeviceProperties {

    public static final String PROPS_DISPLAY = "display";
    public static final String PROPS_INPUT_METHOD = "input_method";

    private static final String KEY_SCREEN_STATE = "mScreenState";

    public static Observable<Map.Entry<String, String>> inputMethodProperties(@NotNull final AdbDevice adbDevice) {
        return Observable.just(adbDevice)
                .flatMap(Adb.INSTANCE::getInputMethodDumpsys)
                .flatMapIterable(Map::entrySet)
                .filter(PropHelper::isValidProperty)
                .sorted(Comparator.comparing(Map.Entry::getKey));
    }

    public static Observable<Map.Entry<String, String>> displayProperties(@NotNull final AdbDevice adbDevice) {
        return Observable.just(adbDevice)
                .flatMap(Adb.INSTANCE::getDisplayDumpsys)
                .flatMapIterable(Map::entrySet)
                .filter(PropHelper::isValidProperty)
                .sorted(Comparator.comparing(Map.Entry::getKey));
    }

    public static boolean isScreenOn(@NotNull final AdbDevice adbDevice) {
        return isScreenOnSingle(adbDevice).blockingGet();
    }

    public static Single<Boolean> isScreenOnSingle(@NotNull final AdbDevice adbDevice) {
        return Observable.just(adbDevice)
                .flatMap(AdbDeviceProperties::displayProperties)
                .filter(entry -> PropHelper.isKey(entry, KEY_SCREEN_STATE))
                .zipWith(Observable.interval(1, TimeUnit.SECONDS), (entry, a) -> entry)
                .map(PropHelper::hasPositiveValue)
                .firstOrError();
    }
}