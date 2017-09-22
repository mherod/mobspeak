package co.herod.adbwrapper.util

import java.io.File

object FileUtil {

    fun getFile(pathname: String): File {

        val file = File(pathname)

        if (file.exists()) {
            return file
        }

        val folder = File(getFolderPath(pathname))
        folder.exists() || folder.mkdirs()
        return file
    }

    private fun getFolderPath(pathname: String): String {

        if (!pathname.contains("/") && pathname.contains(".")) {
            return "./"
        }

        return try {
            val lastIndexOf = pathname.lastIndexOf("/")
            pathname.substring(0, lastIndexOf)
        } catch (e: IndexOutOfBoundsException) {
            pathname
        }

    }
}
