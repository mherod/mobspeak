package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.AdbUiNode
import co.herod.adbwrapper.util.FileUtil
import co.herod.adbwrapper.util.ImageUtil
import co.herod.adbwrapper.util.UiHierarchyHelper
import co.herod.adbwrapper.util.Utils
import io.reactivex.Observable
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.schedulers.Schedulers

import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException

object ScreenshotHelper {

    private val SCREENSHOT_EXPIRY_MILLIS = 10000

    @CheckReturnValue
    private fun AdbDevice.screenshot(ignoreCache: Boolean): File {

        val file = FileUtil.getFile("screen.png")

        val l = FileUtil.getAgeMillis(file)

        if (l < SCREENSHOT_EXPIRY_MILLIS && !ignoreCache) {
            // TODO only expire on change in UI
            return file
        }

        AdbUi.pullScreenCapture(this)
        return file
    }

    internal fun AdbDevice.screenshotUiNode(adbUiNode: AdbUiNode) =
            UiHierarchyHelper.extractBoundsInts(adbUiNode)?.blockingSubscribe { screenshotCoordinates(it) }

//    internal fun AdbDevice.screenshotUiNode(nodeString: String) =
//            UiHierarchyHelper.extractBoundsInts(nodeString)?.blockingSubscribe { screenshotCoordinates(it) }

    private fun AdbDevice.screenshotCoordinates(
            coordinates: Array<Int>
    ) {

        val width = coordinates[2] - coordinates[0]
        val height = coordinates[3] - coordinates[1]

        if (width < 10 || height < 10) {
            return
        }

        this.getBufferedImageObservable(coordinates, width, height)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .doOnNext { bufferedImage -> ImageUtil.saveBufferedImage(bufferedImage, pathForCropImage(coordinates)) }
                .subscribe({ }) { _ -> }
    }

    private fun AdbDevice.getBufferedImageObservable(
            coordinates: Array<Int>,
            width: Int,
            height: Int
    ) = Observable.fromCallable { getBufferedImage(coordinates, width, height) }

    private fun AdbDevice.getBufferedImage(
            coordinates: Array<Int>,
            width: Int,
            height: Int
    ): BufferedImage? {

        val screenshot = this.screenshot(false)
        try {
            return ImageUtil.cropImage(screenshot, coordinates[0], coordinates[1], width, height)
        } catch (e: IOException) {
            e.printStackTrace()
            throw RuntimeException(e)
        }

    }

    private fun pathForCropImage(coordinates: Array<Int>): String {
        return String.format("imgs/screen_sub_%d.png", Utils.intArrayHashcode(coordinates))
    }
}
