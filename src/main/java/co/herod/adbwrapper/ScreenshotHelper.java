package co.herod.adbwrapper;

import co.herod.adbwrapper.model.AdbDevice;
import co.herod.adbwrapper.model.AdbUiNode;
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

public class ScreenshotHelper {

    private static final int SCREENSHOT_EXPIRY_MILLIS = 10000;

    @CheckReturnValue
    public static File screenshot(final AdbDevice adbDevice, final boolean ignoreCache) {

        final File file = FileUtil.getFile("screen.png");

        final long l = FileUtil.getAgeMillis(file);

        if (l < SCREENSHOT_EXPIRY_MILLIS && !ignoreCache) {
            // TODO only expire on change in UI
            return file;
        }

        AdbUi.pullScreenCapture(adbDevice);
        return file;
    }

    static void screenshotUiNode(final AdbDevice connectedAdbDevice, final AdbUiNode adbUiNode) {
        UiHierarchyHelper.extractBoundsInts(adbUiNode.toString()).blockingSubscribe(coords -> screenshotCoords(connectedAdbDevice, coords));
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
                .doOnNext(bufferedImage -> ImageUtil.saveBufferedImage(bufferedImage, pathForCropImage(coords)))
                .subscribe(bufferedImage -> {
                }, throwable -> {
                });
    }

    private static Observable<BufferedImage> getBufferedImageObservable(final AdbDevice connectedAdbDevice, final Integer[] coords, final int width, final int height) {
        return Observable.fromCallable(() -> getBufferedImage(connectedAdbDevice, coords, width, height));
    }

    private static BufferedImage getBufferedImage(final AdbDevice connectedAdbDevice, final Integer[] coords, final int width, final int height) {

        final File screenshot = screenshot(connectedAdbDevice, false);
        try {
            return ImageUtil.cropImage(screenshot, coords[0], coords[1], width, height);
        } catch (final IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static String pathForCropImage(final Integer[] coords) {
        return String.format("imgs/screen_sub_%d.png", Utils.intArrayHashcode(coords));
    }
}
