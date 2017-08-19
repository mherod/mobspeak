package co.herod.adbwrapper.util

import co.herod.adbwrapper.model.AdbUiNode
import io.reactivex.Flowable
import io.reactivex.Observable

object UiHierarchyHelper {

    private val KEY_DELIMITER = "=\""

    private val KEY_STRING_BOUNDS = "bounds".getKeyString()
    private val KEY_STRING_TEXT = "text".getKeyString()

    fun extractText(s: String): String = extract(s, KEY_STRING_TEXT)

    private fun String.getKeyString() = String.format("%s%s", this, KEY_DELIMITER)

    private fun String.extractBounds() = extract(this, KEY_STRING_BOUNDS)
            .replace("][", ",")
            .replace("[^\\d,]".toRegex(), "")

    private fun extract(s: String, s1: String): String {

        try {
            val substring = s.substring(s.indexOf(s1))
            val beginIndex = s1.length
            return substring.substring(beginIndex, substring.substring(beginIndex).indexOf("\"") + beginIndex)
        } catch (exception: StringIndexOutOfBoundsException) {

            System.err.printf("%s %s", s, s1)
            exception.printStackTrace()

            throw exception
        }

    }

    fun centreX(coordinates: Array<Int>) = (coordinates[0] + coordinates[2]) / 2
    fun centreY(coordinates: Array<Int>) = (coordinates[1] + coordinates[3]) / 2

    fun nodeTextContains(s: String?, s1: String?): Boolean =
            s1 == null || s1.isEmpty() || s != null && !s.isEmpty() && extractText(s).contains(s1)

    fun extractBoundsInts(s: AdbUiNode): Observable<Array<Int>>? = s.toString().to(extractBoundsInts(s)).second

    fun extractBoundsInts(s: String): Flowable<Array<Int>>? = Flowable.just(s)
            .map { it.extractBounds() }
            .map { it.split(",".toRegex()).dropLastWhile { it.isEmpty() } }
            .map { it.map { Integer.parseInt(it) } }
            .map { it.toTypedArray() }

    fun uiXmlToNodes(upstream: Observable<String>): Observable<String> = upstream
            .flatMapIterable { it.split(">".toRegex()).dropLastWhile { it.isEmpty() } }
            .map { it.trim { it <= ' ' } }
            .map { s -> if (s.endsWith(">").not()) s + ">"; s }
            .filter { "=" in it }
            .filter { it.contains(KEY_STRING_BOUNDS) }

    fun getHeight(coordinates: Array<Int>) = coordinates[3] - coordinates[1]

    fun getWidth(coordinates: Array<Int>) = coordinates[2] - coordinates[0]

    fun isPackage(packageIdentifier: String, s: String) = "package=\"$packageIdentifier\"" in s

}
