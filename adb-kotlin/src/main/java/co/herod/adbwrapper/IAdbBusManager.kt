package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbUiHierarchy
import co.herod.adbwrapper.model.AdbUiNode

/**
 * Created by matthewherod on 20/08/2017.
 */
interface IAdbBusManager {
    fun getAdbBus(): BusSubject<String>
    fun getAdbUiHierarchyBus(): BusSubject<AdbUiHierarchy>
    fun getAdbUiNodeBus(): BusSubject<AdbUiNode>
}