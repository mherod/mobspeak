/*
 * Copyright (c) 2018. Herod
 */

package co.herod.adbwrapper.util

import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

object ImageUtil {

    @Throws(IOException::class)
    fun saveBufferedImage(bufferedImage: BufferedImage, pathname: String) {
        ImageIO.write(bufferedImage, "PNG", FileUtil.getFile(pathname))
    }

    @Throws(IOException::class)
    private fun readImage(filePath: File): BufferedImage {
        return ImageIO.read(filePath)
    }

    @Throws(IOException::class)
    fun cropImage(filePath: File, x: Int?, y: Int?, w: Int, h: Int): BufferedImage? {
        return x?.let { y?.let { it1 -> readImage(filePath).getSubimage(it, it1, w, h) } }
    }
}
