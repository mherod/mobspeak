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
        file.mkdirs();
        return file;
    }
}
