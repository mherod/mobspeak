package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbUiHierarchy
import co.herod.adbwrapper.model.UiNode
import co.herod.adbwrapper.util.Utils
import io.reactivex.Observable

object AdbBusManager : IAdbBusManager {

    private val _adbBus: BusSubject<String> = MainBusSubject()
    private val _adbUiHierarchyBus: BusSubject<AdbUiHierarchy> = AdbUiHierarchyBus()
    private val __UI_NODE_BUS: BusSubject<UiNode> = AdbUiNodeBus()

    override fun getAdbBus(): BusSubject<String> = _adbBus
    override fun getAdbUiHierarchyBus(): BusSubject<AdbUiHierarchy> = _adbUiHierarchyBus
    override fun getAdbUiNodeBus(): BusSubject<UiNode> = __UI_NODE_BUS

    internal fun throttledBus(): Observable<String>? = getAdbBus().concatMap { Utils.throttleOutput(it) }
}

class MainBusSubject : BusSubject<String>()
class AdbUiHierarchyBus : BusSubject<AdbUiHierarchy>()
class AdbUiNodeBus : BusSubject<UiNode>()
