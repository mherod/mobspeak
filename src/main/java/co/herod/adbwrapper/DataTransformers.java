package co.herod.adbwrapper;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DataTransformers {

    static SingleSource<? extends Map<String, String>> entryListToMapSingle(List<Map.Entry<String, String>> entries) {
        return Single.just(entryListToMap(entries));
    }

    @NotNull
    private static Map<String, String> entryListToMap(List<Map.Entry<String, String>> entries) {
        Map<String, String> propertyMap = new HashMap<>();
        for (Map.Entry<String, String> entry : entries) {
            propertyMap.put(entry.getKey(), entry.getValue());
        }
        return propertyMap;
    }
}
