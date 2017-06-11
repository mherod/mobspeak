package co.herod.adbwrapper;

import co.herod.adbwrapper.model.AdbDevice;
import co.herod.adbwrapper.util.UiHierarchyHelper;
import io.reactivex.Observable;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.schedulers.Schedulers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ScreenshotHelper {

    @CheckReturnValue
    public static File screenshot(final AdbDevice adbDevice) {

        final File file = new File("screen.png");

        final long l = System.currentTimeMillis() - (file.exists() ? file.lastModified() : 0);

        if (l < 10000) {

            System.out.printf("Screenshot time diff is %d \n", l);
            return file;
        }

        ProcessHelper.observableProcess(AdbProcesses.screenCaptureTake(adbDevice))
                .toList()
                .flatMap(strings -> ProcessHelper.observableProcess(AdbProcesses.screenCapturePull(adbDevice)).toList())
                .blockingGet();

        return file;
    }

    public static BufferedImage getSubimage(final File filePath, final int x, final int y, final int w, final int h) {

        try {
            final BufferedImage originalImage = ImageIO.read(filePath);
            return originalImage.getSubimage(x, y, w, h);
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveBufferedImage(final BufferedImage bufferedImage, final String pathname) {
        try {
            ImageIO.write(bufferedImage, "PNG", new File(pathname));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    static void screenshotUiNode(final AdbDevice connectedAdbDevice, final String s) {
        UiHierarchyHelper.extractBoundsInts(s).blockingSubscribe(coords -> saveScreenshot(connectedAdbDevice, coords));
    }

    private static void saveScreenshot(final AdbDevice connectedAdbDevice, final Integer[] coords) {
        final int width = coords[2] - coords[0];
        final int height = coords[3] - coords[1];

        if (width < 10 || height < 10) {
            return;
        }

        getBufferedImageObservable(connectedAdbDevice, coords, width, height)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(bufferedImage -> getaVoid(coords, bufferedImage));
    }

    private static Observable<BufferedImage> getBufferedImageObservable(final AdbDevice connectedAdbDevice, final Integer[] coords, final int width, final int height) {
        return Observable.fromCallable(() -> getBufferedImage(connectedAdbDevice, coords, width, height));
    }

    private static void getaVoid(final Integer[] coords, final BufferedImage bufferedImage) {
        saveBufferedImage(bufferedImage, String.format("imgs/screen_sub_%d.png", Arrays.toString(coords).hashCode()));
    }

    private static BufferedImage getBufferedImage(final AdbDevice connectedAdbDevice, final Integer[] coords, final int width, final int height) {
        final File screenshot = screenshot(connectedAdbDevice);
        return getSubimage(screenshot, coords[0], coords[1], width, height);
    }
}
