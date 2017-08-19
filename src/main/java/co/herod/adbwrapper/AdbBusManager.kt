package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbUiHierarchy
import co.herod.adbwrapper.model.AdbUiNode
import co.herod.adbwrapper.util.Utils
import io.reactivex.Observable

/**
 * Created by matthewherod on 23/04/2017.
 */
object AdbBusManager {

    val ADB_BUS: BusSubject<String> = MainBusSubject()

    val ADB_UI_HIERARCHY_BUS: BusSubject<AdbUiHierarchy> = AdbUiHierarchyBus()

    val ADB_UI_NODE_BUS: BusSubject<AdbUiNode> = AdbUiNodeBus()

    internal fun throttledBus(): Observable<String> = ADB_BUS.concatMap({ Utils.throttleOutput(it) })

    private class MainBusSubject : BusSubject<String>()

    private class AdbUiHierarchyBus : BusSubject<AdbUiHierarchy>()

    private class AdbUiNodeBus : BusSubject<AdbUiNode>()
}
