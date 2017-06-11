package co.herod.adbwrapper;

import co.herod.adbwrapper.model.AdbDevice;
import co.herod.adbwrapper.util.FileUtil;
import co.herod.adbwrapper.util.ImageUtil;
import co.herod.adbwrapper.util.UiHierarchyHelper;
import co.herod.adbwrapper.util.Utils;
import io.reactivex.Observable;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.schedulers.Schedulers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ScreenshotHelper {

    public static final int SCREENSHOT_EXPIRY_MILLIS = 10000;

    @CheckReturnValue
    public static File screenshot(final AdbDevice adbDevice) {

        final File file = FileUtil.getFile("screen.png");

        final long l = FileUtil.getAgeMillis(file);

        if (l < SCREENSHOT_EXPIRY_MILLIS) {

            System.out.printf("Screenshot time diff is %d \n", l);
            return file;
        }

        ProcessHelper.blocking(AdbProcesses.screenCaptureTake(adbDevice), 10, TimeUnit.SECONDS);
        ProcessHelper.blocking(AdbProcesses.screenCapturePull(adbDevice), 10, TimeUnit.SECONDS);

        return file;
    }

    public static BufferedImage getSubimage(final File filePath, final int x, final int y, final int w, final int h) {

        try {
            final BufferedImage originalImage = ImageUtil.readImage(filePath);
            return originalImage.getSubimage(x, y, w, h);
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    static void screenshotUiNode(final AdbDevice connectedAdbDevice, final String nodeString) {
        UiHierarchyHelper.extractBoundsInts(nodeString).blockingSubscribe(coords -> screenshotCoords(connectedAdbDevice, coords));
    }

    private static void screenshotCoords(final AdbDevice connectedAdbDevice, final Integer[] coords) {

        final int width = UiHierarchyHelper.getWidth(coords);
        final int height = UiHierarchyHelper.getHeight(coords);

        if (width < 10 || height < 10) {
            return;
        }

        getBufferedImageObservable(connectedAdbDevice, coords, width, height)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(bufferedImage -> ImageUtil.saveBufferedImage(bufferedImage, pathForCropImage(coords)));
    }

    private static Observable<BufferedImage> getBufferedImageObservable(final AdbDevice connectedAdbDevice, final Integer[] coords, final int width, final int height) {
        return Observable.fromCallable(() -> getBufferedImage(connectedAdbDevice, coords, width, height));
    }

    private static BufferedImage getBufferedImage(final AdbDevice connectedAdbDevice, final Integer[] coords, final int width, final int height) {
        final File screenshot = screenshot(connectedAdbDevice);
        return getSubimage(screenshot, coords[0], coords[1], width, height);
    }

    private static String pathForCropImage(final Integer[] coords) {
        return String.format("imgs/screen_sub_%d.png", Utils.intArrayHashcode(coords));
    }
}
