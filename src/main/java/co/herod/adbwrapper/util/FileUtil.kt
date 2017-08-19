package co.herod.adbwrapper.util

import java.io.File

object FileUtil {

    fun getAgeMillis(file: File): Long {
        return System.currentTimeMillis() - if (file.exists()) file.lastModified() else 0
    }

    fun getFile(pathname: String): File {

        val file = File(pathname)

        if (file.exists()) {
            return file
        }

        val folder = File(getFolderPath(pathname))
        val g = folder.exists() || folder.mkdirs()

        return file
    }

    private fun getFolderPath(pathname: String): String {

        if (!pathname.contains("/") && pathname.contains(".")) {
            return "./"
        }

        try {
            val lastIndexOf = pathname.lastIndexOf("/")
            return pathname.substring(0, lastIndexOf)
        } catch (e: IndexOutOfBoundsException) {
            return pathname
        }

    }
}
