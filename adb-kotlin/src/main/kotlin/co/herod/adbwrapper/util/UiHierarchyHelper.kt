package co.herod.adbwrapper.util

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.AdbUiHierarchy
import co.herod.adbwrapper.model.UiBounds
import co.herod.adbwrapper.model.UiNode
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*

object UiHierarchyHelper {

    private const val KEY_DELIMITER = "=\""
    private const val KEY_BOUNDS = "bounds"
    private const val KEY_TEXT = "text"
    private const val KEY_CONTENT_DESCRIPTION = "content-desc"

    private val KEY_BOUNDS_DELIM = KEY_BOUNDS + KEY_DELIMITER
    private val KEY_TEXT_DELIM = KEY_TEXT + KEY_DELIMITER
    private val KEY_CONTENT_DESCRIPTION_DELIM = KEY_CONTENT_DESCRIPTION + KEY_DELIMITER

    fun extractContentDescription(s: String): String = extract(s, KEY_CONTENT_DESCRIPTION_DELIM)

    fun extractText(s: String): String = extract(s, KEY_TEXT_DELIM)

    private fun String.extractBounds() = extract(this, KEY_BOUNDS_DELIM)
            .replace("][", ",")
            .replace("[^\\d,]".toRegex(), "")

    private fun extract(s: String, s1: String): String {

        try {
            val substring = s.substring(s.indexOf(s1))
            val beginIndex = s1.length
            return substring.substring(beginIndex, substring.substring(beginIndex).indexOf("\"") + beginIndex)
        } catch (exception: StringIndexOutOfBoundsException) {
            exception.printStackTrace()
            throw exception
        }
    }

    fun centreX(coordinates: Array<Int>): Int = (coordinates[0] + coordinates[2]) / 2
    fun centreY(coordinates: Array<Int>): Int = (coordinates[1] + coordinates[3]) / 2

    fun nodeTextContains(s: String?, s1: String?): Boolean =
            s1 == null || s1.isEmpty() || s != null && !s.isEmpty() && s1 in extractText(s)

    fun extractBoundsInts(s: UiNode): Observable<Array<Int>>? = s.toString().to(extractBoundsInts(s)).second

    fun extractBoundsInts(s: String): Single<UiBounds> = Observable.just(s)
            .map { it.extractBounds() }
            .map { it.split(",".toRegex()).dropLastWhile { it.isEmpty() } }
            .map { it.map { Integer.parseInt(it) } }
            .map { it.toTypedArray() }
            .map { UiBounds(it) }
            .singleOrError()

    fun uiXmlToNodes(upstream: Observable<String>): Observable<String> = upstream
            .flatMapIterable { it.split(">".toRegex()).dropLastWhile { it.isEmpty() } }
            .map { it.trim() }
            .map { s -> if (s.endsWith(">").not()) s + ">"; s }
            .filter { "=" in it }
            .filter { KEY_BOUNDS_DELIM in it }

    fun rawDumpToNodes(upstream: Observable<String>, adbDevice: AdbDevice?): Observable<UiNode>? = upstream
            .filter { Objects.nonNull(it) }
            .map { AdbUiHierarchy(it, adbDevice) }
            .map { it.xmlString }
            .compose { UiHierarchyHelper.uiXmlToNodes(it) }
            .map { UiNode(it) }
            .filter { Objects.nonNull(it) }

    fun isPackage(packageIdentifier: String, s: String) = "package=\"$packageIdentifier\"" in s
}
