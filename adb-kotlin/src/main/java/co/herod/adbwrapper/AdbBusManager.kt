package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbUiHierarchy
import co.herod.adbwrapper.model.AdbUiNode
import co.herod.adbwrapper.util.Utils
import io.reactivex.Observable

/**
 * Created by matthewherod on 23/04/2017.
 */
object AdbBusManager : IAdbBusManager {

    private val _ADB_BUS: BusSubject<String> = MainBusSubject()
    override fun getAdbBus(): BusSubject<String> = _ADB_BUS

    private val _ADB_UI_HIERARCHY_BUS: BusSubject<AdbUiHierarchy> = AdbUiHierarchyBus()
    override fun getAdbUiHierarchyBus(): BusSubject<AdbUiHierarchy> = _ADB_UI_HIERARCHY_BUS

    private val _ADB_UI_NODE_BUS: BusSubject<AdbUiNode> = AdbUiNodeBus()
    override fun getAdbUiNodeBus(): BusSubject<AdbUiNode> = _ADB_UI_NODE_BUS

    internal fun throttledBus(): Observable<String>? = getAdbBus().concatMap { Utils.throttleOutput(it) }

    private class MainBusSubject : BusSubject<String>()

    private class AdbUiHierarchyBus : BusSubject<AdbUiHierarchy>()

    private class AdbUiNodeBus : BusSubject<AdbUiNode>()
}
