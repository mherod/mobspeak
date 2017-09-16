package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbUiHierarchy
import co.herod.adbwrapper.model.UiNode

/**
 * Created by matthewherod turnOn 20/08/2017.
 */
interface IAdbBusManager {
    fun getAdbBus(): BusSubject<String>
    fun getAdbUiHierarchyBus(): BusSubject<AdbUiHierarchy>
    fun getAdbUiNodeBus(): BusSubject<UiNode>
}