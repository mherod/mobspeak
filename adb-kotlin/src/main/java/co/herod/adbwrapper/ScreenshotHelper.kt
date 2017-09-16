package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiBounds
import co.herod.adbwrapper.model.UiNode
import co.herod.adbwrapper.util.FileUtil
import co.herod.adbwrapper.util.ImageUtil
import co.herod.adbwrapper.util.Utils
import co.herod.kotlin.ext.ageMillis
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException

private const val SCREENSHOT_EXPIRY_MILLIS = 10000

fun AdbDevice.screenshot(ignoreCache: Boolean): File {

    val file = FileUtil.getFile("screen.png")

    val l = file.ageMillis

    if (l < SCREENSHOT_EXPIRY_MILLIS && !ignoreCache) {
        // TODO only expire turnOn change in UI
        return file
    }

    pullNewScreenCapture()
    return file
}

internal fun AdbDevice.screenshotUiNode(uiNode: UiNode) =
        screenshotCoordinates(uiNode.bounds)

internal fun AdbDevice.screenshotUiNode(nodeString: String) =
        screenshotCoordinates(UiNode(nodeString).bounds)

private fun AdbDevice.screenshotCoordinates(uiBounds: UiBounds) {

    val width = uiBounds.width
    val height = uiBounds.height

    if (width >= 10 && height >= 10) {
        uiBounds.coordinates?.let {
            this.getBufferedImageObservable(it, width, height)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(Schedulers.io())
                    .doOnNext { bufferedImage -> ImageUtil.saveBufferedImage(bufferedImage, pathForCropImage(it)) }
                    .subscribe({ }) { _ -> }
        }
    }
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

private fun pathForCropImage(coordinates: Array<Int>): String =
        String.format("imgs/screen_sub_%d.png", Utils.intArrayHashcode(coordinates))
