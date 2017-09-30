@file:Suppress("unused")

package co.herod.adbwrapper.util

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiHierarchy
import co.herod.adbwrapper.model.UiNode
import io.reactivex.Observable
import java.util.*

object UiHelper {

    private const val KEY_DELIMITER = "=\""

    fun extractProperty(s: String, s1: String): String = extract(s, s1 + KEY_DELIMITER)

    fun extractBoundsInts(s: String) = extractProperty(s, "bounds")
            .replace("][", ",")
            .replace("[^\\d,]".toRegex(), "")
            .split(",")
            .dropLastWhile { it.isEmpty() }
            .map { Integer.parseInt(it) }
            .toIntArray()

    fun uiXmlToNodes(
            upstream: Observable<String>,
            date: Date = Date(),
            adbDevice: AdbDevice?
    ): Observable<UiNode> = upstream
            .flatMapIterable { it.split(">").dropLastWhile { it.isEmpty() } }
            .map { it.trim() }
            .map { s -> if (s.endsWith(">").not()) s + ">"; s }
            .map { it.trim() }
            .filter { "=" in it }
            .filter { "bounds" + KEY_DELIMITER in it }
            .map { UiNode(adbDevice, it, date) }

    fun rawDumpToNodes(
            upstream: Observable<String>,
            adbDevice: AdbDevice?
    ): Observable<UiNode>? = upstream
            .filter { Objects.nonNull(it) }
            .map { UiHierarchy(adbDevice, it) }
            .map { it.xmlString }
            .compose { uiXmlToNodes(it, adbDevice = adbDevice) }
            .filter { Objects.nonNull(it) }

    private fun extract(s: String, s1: String): String = try {
        s.substring(s.indexOf(s1) + s1.length).substringBefore("\"")
    } catch (exception: StringIndexOutOfBoundsException) {
        throw exception
    }
}
