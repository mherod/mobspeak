package co.herod.kotlin.ext

import java.io.File

val File.ageMillis: Long
    get() = System.currentTimeMillis() - if (exists()) lastModified() else 0