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

    fun streamUiHierarchyUpdates() = adbBus()?.filter { it.isUiDumpOutput() }

    fun streamAdbCommands() = adbBus()?.filter { it.isAdbInput() }

    fun streamUiNodeChanges(): Observable<AdbUiNode>? = streamUiHierarchyUpdates()?.let { observable ->
        observable
                .map { it.parseNode() }
                .compose(ResultChangeFixedDurationTransformer())
                .compose { UiHierarchyHelper.uiXmlToNodes(it) }
                .onErrorReturn { "" }
                .filter { it.hasValue() }
                .compose(FixedDurationTransformer(1, TimeUnit.DAYS))
                .map { AdbUiNode(it) }
    }

    private fun String.hasValue(): Boolean = !trim().isEmpty()

    private fun String.isAdbInput(): Boolean = startsWith("adb ") && trim() != "Killed"

    private fun String.isUiDumpOutput(): Boolean = contains("<node") && contains("bounds=")

    private fun String.parseNode(): String = substring(indexOf('<'), lastIndexOf('>') + 1)

}
