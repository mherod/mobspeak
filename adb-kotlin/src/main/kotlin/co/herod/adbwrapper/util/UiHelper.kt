package co.herod.adbwrapper.util

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.AdbUiHierarchy
import co.herod.adbwrapper.model.UiNode
import io.reactivex.Observable
import java.util.*

object UiHelper {

    private const val KEY_DELIMITER = "=\""

    fun extractProperty(s: String, s1: String): String {
        return extract(s, s1 + KEY_DELIMITER)
    }

    fun extractBoundsInts(s: String) = extractProperty(s, "bounds")
            .replace("][", ",")
            .replace("[^\\d,]".toRegex(), "")
            .split(",")
            .dropLastWhile { it.isEmpty() }
            .map { Integer.parseInt(it) }
            .toIntArray()

    fun uiXmlToNodes(upstream: Observable<String>): Observable<UiNode> = upstream
            .flatMapIterable { it.split(">").dropLastWhile { it.isEmpty() } }
            .map { it.trim() }
            .map { s -> if (s.endsWith(">").not()) s + ">"; s }
            .map { it.trim() }
            .filter { "=" in it }
            .filter { "bounds" + KEY_DELIMITER in it }
            .map { UiNode(it) }

    fun rawDumpToNodes(upstream: Observable<String>, adbDevice: AdbDevice?): Observable<UiNode>? = upstream
            .filter { Objects.nonNull(it) }
            .map { AdbUiHierarchy(it, adbDevice) }
            .map { it.xmlString }
            .compose { UiHelper.uiXmlToNodes(it) }
            .filter { Objects.nonNull(it) }

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
}
