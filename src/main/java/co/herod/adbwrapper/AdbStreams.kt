package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbUiNode
import co.herod.adbwrapper.rx.FixedDurationTransformer
import co.herod.adbwrapper.rx.ResultChangeFixedDurationTransformer
import co.herod.adbwrapper.util.UiHierarchyHelper
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

@Suppress("MemberVisibilityCanPrivate")
object AdbStreams {

    private fun adbBus() = AdbBusManager.throttledBus()

    fun streamUiHierarchyUpdates() = adbBus().filter { AdbFilters.isUiDumpOutput(it) }!!

    fun streamAdbCommands() = adbBus().filter { AdbFilters.isAdbInput(it) }!!

    fun streamUiNodeChanges(): Observable<AdbUiNode>? {

        return streamUiHierarchyUpdates()
                .map { parseNode(it) }
                .compose(ResultChangeFixedDurationTransformer())
                .compose { UiHierarchyHelper.uiXmlToNodes(it) }
                .onErrorReturn { "" }
                .filter { hasValue(it) }
                .compose(FixedDurationTransformer(1, TimeUnit.DAYS))
                .map { AdbUiNode(it) }
    }

    private fun hasValue(it: String) = !it.trim { it <= ' ' }.isEmpty()

    private fun parseNode(it: String) = it.substring(it.indexOf('<'), it.lastIndexOf('>') + 1)

    internal object AdbFilters {

        fun isAdbInput(s: String) = s.startsWith("adb ") && s.trim { it <= ' ' } != "Killed"

        fun isUiDumpOutput(s: String) = s.contains("<node") && s.contains("bounds=")

    }
}
