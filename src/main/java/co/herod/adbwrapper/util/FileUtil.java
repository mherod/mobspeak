package co.herod.adbwrapper.util;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class FileUtil {

    public static long getAgeMillis(final File file) {
        return System.currentTimeMillis() - (file.exists() ? file.lastModified() : 0);
    }

    @NotNull
    public static File getFile(final String pathname) {
        final File file = new File(pathname);

        if (file.exists()) {
            return file;
        }

        final File folder = new File(getFolderPath(pathname));
        final boolean g = folder.exists() || folder.mkdirs();

        return file;
    }

    @NotNull
    private static String getFolderPath(final String pathname) {

        if (!pathname.contains("/") && pathname.contains(".")) {
            return "./";
        }

        try {
            final int lastIndexOf = pathname.lastIndexOf("/");
            return pathname.substring(0, lastIndexOf);
        } catch (final IndexOutOfBoundsException e) {
            return pathname;
        }
    }
}
