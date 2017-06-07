package co.herod.adbwrapper;

import java.util.Map;

@SuppressWarnings("WeakerAccess")
class PropHelper {

    static boolean hasPositiveValue(Map.Entry<String, String> entry) {
        return hasPositiveValue(entry.getValue());
    }

    static boolean hasPositiveValue(String value) {

        value = value.trim().toLowerCase();

        return value.equals("on") || value.equals("1") || Boolean.parseBoolean(value);
    }

    static boolean isValidProperty(Map.Entry<String, String> a) {
        return !a.getKey().contains(" ");
    }

    static boolean isKey(Map.Entry<String, String> entry, String key) {
        return entry.getKey().equals(key);
    }
}
