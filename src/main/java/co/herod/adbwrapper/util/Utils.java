package co.herod.adbwrapper.util;

import co.herod.adbwrapper.model.AdbDevice;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static Observable<String> throttleOutput(@NotNull final String s) {
        return Observable.timer(10, TimeUnit.MILLISECONDS)
                .flatMap(a -> Observable.just(s));
    }

    @NotNull
    static Map<String, String> entryListToMap(@NotNull final List<Map.Entry<String, String>> entries) {
        final Map<String, String> propertyMap = new HashMap<>();
        for (final Map.Entry<String, String> entry : entries) {
            propertyMap.put(entry.getKey(), entry.getValue());
        }
        return propertyMap;
    }

    static SingleSource<? extends Map<String, String>> entryListToMapSingle(@NotNull final List<Map.Entry<String, String>> entries) {
        return Single.just(entryListToMap(entries));
    }

    private static String outputEntry(@NotNull final Map.Entry<String, String> entry) {
        return String.format("%s is %s", entry.getKey(), entry.getValue());
    }

    private static Map<String, String> propertyMap(@NotNull final AdbDevice adbDevice, @NotNull final Function<AdbDevice, ObservableSource<? extends Map.Entry<String, String>>> properties) {

        return Observable.just(adbDevice)
                .flatMap(properties)
                .toList()
                .flatMap(Utils::entryListToMapSingle)
                .blockingGet();
    }

    public static int intArrayHashcode(final Integer[] coords) {
        return Arrays.toString(coords).hashCode();
    }
}
