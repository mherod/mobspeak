package co.herod.kotlin.ext

fun String.containsIgnoreCase(s: String): Boolean = s.toLowerCase() in this.toLowerCase()
fun String.crop(s1: Char, s2: Char) = substringBeforeLast(s2).substringAfterLast(s1)
fun String.containsAll(vararg chars: Char) =
        chars.all { c -> c in this }
fun List<String>.toIntList(): List<Int> = map { Integer.parseInt(it) }
fun List<String>.toIntArray(): IntArray = toIntList().toIntArray()