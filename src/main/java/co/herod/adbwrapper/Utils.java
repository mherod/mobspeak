package co.herod.adbwrapper;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Utils {

    static boolean isNotEmpty(String s) {
        return !s.trim().isEmpty();
    }

    static Observable<String> throttleOutput(String s) {
        return Observable.timer(10, TimeUnit.MILLISECONDS)
                .flatMap(a -> Observable.just(s));
    }

    @NotNull
    static Integer[] stringArrayToIntArray(String[] strings) {
        Integer[] integers = new Integer[4];
        for (int i = 0; i < 4; i++) {
            integers[i] = Integer.parseInt(strings[i]);
        }
        return integers;
    }

    @NotNull
    static Map<String, String> entryListToMap(List<Map.Entry<String, String>> entries) {
        Map<String, String> propertyMap = new HashMap<>();
        for (Map.Entry<String, String> entry : entries) {
            propertyMap.put(entry.getKey(), entry.getValue());
        }
        return propertyMap;
    }

    static SingleSource<? extends Map<String, String>> entryListToMapSingle(List<Map.Entry<String, String>> entries) {
        return Single.just(entryListToMap(entries));
    }

    private static String outputEntry(Map.Entry<String, String> entry) {
        return String.format("%s is %s", entry.getKey(), entry.getValue());
    }

    private static Map<String, String> propertyMap(Device device, Function<Device, ObservableSource<? extends Map.Entry<String, String>>> properties) {

        return Observable.just(device)
                .flatMap(properties)
                .toList()
                .flatMap(Utils::entryListToMapSingle)
                .blockingGet();
    }
}
