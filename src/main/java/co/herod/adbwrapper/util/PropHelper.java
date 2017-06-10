package co.herod.adbwrapper.util;

import java.util.Map;

@SuppressWarnings("WeakerAccess")
public class PropHelper {

    public static boolean hasPositiveValue(Map.Entry<String, String> entry) {
        return hasPositiveValue(entry.getValue());
    }

    static boolean hasPositiveValue(String value) {

        value = value.trim().toLowerCase();

        return value.equals("on") || value.equals("1") || Boolean.parseBoolean(value);
    }

    public static boolean isValidProperty(Map.Entry<String, String> a) {
        return !a.getKey().contains(" ");
    }

    public static boolean isKey(Map.Entry<String, String> entry, String key) {
        return entry.getKey().equals(key);
    }
}
