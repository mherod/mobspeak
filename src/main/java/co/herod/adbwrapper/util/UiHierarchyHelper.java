package co.herod.adbwrapper.util;

import io.reactivex.Observable;
import org.jetbrains.annotations.NotNull;

public class UiHierarchyHelper {

    private static final String KEY_DELIMITER = "=\"";

    private static final String KEY_STRING_BOUNDS = getKeyString("bounds");
    private static final String KEY_STRING_TEXT = getKeyString("text");

    private static String getKeyString(final String s) {
        return String.format("%s%s", s, KEY_DELIMITER);
    }

    @NotNull
    public static String extractBounds(String s) {

        return extract(s, KEY_STRING_BOUNDS)
                .replace("][", ",")
                .replaceAll("[^\\d,]", "");
    }

    @NotNull
    private static String extractText(String s) {
        return extract(s, KEY_STRING_TEXT);
    }

    @NotNull
    private static String extract(String s, String s1) {

        try {
            String substring = s.substring(s.indexOf(s1));
            int beginIndex = s1.length();
            return substring.substring(beginIndex, substring.substring(beginIndex).indexOf("\"") + beginIndex);
        } catch (StringIndexOutOfBoundsException exception) {

            System.err.printf("%s %s", s, s1);
            exception.printStackTrace();

            throw exception;
        }
    }

    public static int centreX(Integer[] coords) {
        return (coords[0] + coords[2]) / 2;
    }

    public static int centreY(Integer[] coords) {
        return (coords[1] + coords[3]) / 2;
    }

    static boolean nodeTextContains(String s, String s1) {
        return s1 == null || s1.isEmpty() || s != null && !s.isEmpty() && extractText(s).contains(s1);
    }

    private static boolean hasBoundsProperty(String s) {
        return s.contains(KEY_STRING_BOUNDS);
    }

    public static Observable<String> uiXmlToNodes(Observable<String> upstream) {
        return upstream.flatMapIterable(StringUtils::splitOnCloseTag)
                .map(String::trim)
                .map(StringUtils::appendCloseTagIfNotExists)
                .filter(StringUtils::containsKeyValueSeparator)
                .filter(UiHierarchyHelper::hasBoundsProperty);
    }
}
