package co.herod.kotlin.ext

fun String.containsIgnoreCase(s: String): Boolean = s.toLowerCase() in this