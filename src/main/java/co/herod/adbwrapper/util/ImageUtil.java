package co.herod.adbwrapper.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtil {

    public static void saveBufferedImage(final BufferedImage bufferedImage, final String pathname) throws IOException {
        ImageIO.write(bufferedImage, "PNG", FileUtil.getFile(pathname));
    }

    public static BufferedImage readImage(final File filePath) throws IOException {
        return ImageIO.read(filePath);
    }

    public static BufferedImage cropImage(final File filePath, final int x, final int y, final int w, final int h) throws IOException {
        return readImage(filePath).getSubimage(x, y, w, h);
    }
}
