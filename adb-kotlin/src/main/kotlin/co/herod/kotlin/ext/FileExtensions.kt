package co.herod.kotlin.ext

import java.io.File

val File.ageMillis: Long
    get() = System.currentTimeMillis() - if (exists()) lastModified() else 0

fun File.assertExists() {
    assert(exists()) { "File does not exist at ${toPath()}" }
}