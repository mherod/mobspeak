package co.herod.adbwrapper.util;

import io.reactivex.Observable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UiHierarchyHelper {

    private static final String KEY_DELIMITER = "=\"";

    private static final String KEY_STRING_BOUNDS = getKeyString("bounds");
    private static final String KEY_STRING_TEXT = getKeyString("text");

    private static String getKeyString(final String s) {
        return String.format("%s%s", s, KEY_DELIMITER);
    }

    @NotNull
    private static String extractBounds(@NotNull final String s) {

        return extract(s, KEY_STRING_BOUNDS)
                .replace("][", ",")
                .replaceAll("[^\\d,]", "");
    }

    @NotNull
    private static String extractText(@NotNull final String s) {
        return extract(s, KEY_STRING_TEXT);
    }

    @NotNull
    private static String extract(@NotNull final String s, @NotNull final String s1) {

        try {
            final String substring = s.substring(s.indexOf(s1));
            final int beginIndex = s1.length();
            return substring.substring(beginIndex, substring.substring(beginIndex).indexOf("\"") + beginIndex);
        } catch (final StringIndexOutOfBoundsException exception) {

            System.err.printf("%s %s", s, s1);
            exception.printStackTrace();

            throw exception;
        }
    }

    public static int centreX(final Integer[] coords) {
        return (coords[0] + coords[2]) / 2;
    }

    public static int centreY(final Integer[] coords) {
        return (coords[1] + coords[3]) / 2;
    }

    public static boolean nodeTextContains(@Nullable final String s, @Nullable final String s1) {
        return s1 == null || s1.isEmpty() || s != null && !s.isEmpty() && extractText(s).contains(s1);
    }

    private static boolean hasBoundsProperty(@NotNull final String s) {
        return s.contains(KEY_STRING_BOUNDS);
    }

    public static Observable<Integer[]> extractBoundsInts(@NotNull final String s) {

        return Observable.just(s)
                .map(UiHierarchyHelper::extractBounds)
                .map(StringUtils::splitCsv)
                .map(StringUtils::stringArrayToIntArray);
    }

    public static Observable<String> uiXmlToNodes(@NotNull final Observable<String> upstream) {
        return upstream.flatMapIterable(StringUtils::splitOnCloseTag)
                .map(String::trim)
                .map(StringUtils::appendCloseTagIfNotExists)
                .filter(StringUtils::containsKeyValueSeparator)
                .filter(UiHierarchyHelper::hasBoundsProperty);
    }

    public static boolean isPackage(final String packageIdentifier, final String s) {
        return s.contains("package=\"" + packageIdentifier + "\"");
    }
}
