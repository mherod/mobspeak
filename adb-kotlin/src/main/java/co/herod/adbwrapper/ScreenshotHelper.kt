package co.herod.adbwrapper

import co.herod.adbwrapper.ext.pullCapture
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiBounds
import co.herod.adbwrapper.model.UiNode
import co.herod.adbwrapper.util.FileUtil
import co.herod.adbwrapper.util.ImageUtil
import co.herod.kotlin.ext.ageMillis
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.util.*

private const val SCREENSHOT_EXPIRY_MILLIS = 10000

fun AdbDevice.screenshot(ignoreCache: Boolean): File {

    val file = FileUtil.getFile("screen.png")

    val l = file.ageMillis

    if (l < SCREENSHOT_EXPIRY_MILLIS && !ignoreCache) {
        // TODO only expire turnOn change in UI
        return file
    }

    pullCapture()
    return file
}

internal fun AdbDevice.screenshot(nodeString: String) =
        ScreenshotHelper.screenshot(this, UiNode(nodeString).bounds)

internal fun AdbDevice.screenshot(uiNode: UiNode) =
        ScreenshotHelper.screenshot(this, uiNode.bounds)

object ScreenshotHelper {

    fun screenshot(adbDevice: AdbDevice, uiBounds: UiBounds) {

        val width = uiBounds.width
        val height = uiBounds.height

        if (width >= 10 && height >= 10) {
            uiBounds.coordinates?.let { bounds: Array<Int> ->

                Observable.fromCallable {
                    getBufferedImage(
                            adbDevice,
                            bounds,
                            width,
                            height
                    )
                }
                        .subscribeOn(Schedulers.computation())
                        .observeOn(Schedulers.io())
                        .doOnNext { bufferedImage -> bufferedImage?.let { it1: BufferedImage -> ImageUtil.saveBufferedImage(it1, pathForCropImage(bounds)) } }
                        .subscribe({ }) { _ -> }
            }
        }
    }

    private fun getBufferedImage(
            adbDevice: AdbDevice,
            coordinates: Array<Int>,
            width: Int,
            height: Int
    ): BufferedImage? {

        val screenshot = adbDevice.screenshot(false)
        try {
            return ImageUtil.cropImage(screenshot, coordinates[0], coordinates[1], width, height)
        } catch (e: IOException) {
            e.printStackTrace()
            throw RuntimeException(e)
        }
    }
}

private fun pathForCropImage(coordinates: Array<Int>): String =
        String.format("imgs/screen_sub_%d.png", Arrays.toString(coordinates).hashCode())
