package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbUiHierarchy
import co.herod.adbwrapper.model.AdbUiNode
import co.herod.adbwrapper.util.Utils
import io.reactivex.Observable

/**
 * Created by matthewherod on 23/04/2017.
 */
object AdbBusManager : IAdbBusManager {

    private val _adbBus: BusSubject<String> = MainBusSubject()
    private val _adbUiHierarchyBus: BusSubject<AdbUiHierarchy> = AdbUiHierarchyBus()
    private val _adbUiNodeBus: BusSubject<AdbUiNode> = AdbUiNodeBus()

    override fun getAdbBus(): BusSubject<String> = _adbBus
    override fun getAdbUiHierarchyBus(): BusSubject<AdbUiHierarchy> = _adbUiHierarchyBus
    override fun getAdbUiNodeBus(): BusSubject<AdbUiNode> = _adbUiNodeBus

    internal fun throttledBus(): Observable<String>? = getAdbBus().concatMap { Utils.throttleOutput(it) }
}

class MainBusSubject : BusSubject<String>()
class AdbUiHierarchyBus : BusSubject<AdbUiHierarchy>()
class AdbUiNodeBus : BusSubject<AdbUiNode>()
