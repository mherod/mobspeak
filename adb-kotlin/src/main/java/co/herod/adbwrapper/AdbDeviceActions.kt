package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.AdbUiNode
import co.herod.adbwrapper.util.UiHierarchyHelper
import io.reactivex.Observable

object AdbDeviceActions {

    private const val KEY_EVENT_HOME = 3
    private const val KEY_EVENT_BACK = 4
    private const val KEY_EVENT_POWER = 26
    private const val KEY_EVENT_BACKSPACE = 67

    fun pressHomeButton(adbDevice: AdbDevice) {
        Adb.pressKeyBlocking(adbDevice, KEY_EVENT_HOME)
    }

    fun pressBackButton(adbDevice: AdbDevice) {
        Adb.pressKeyBlocking(adbDevice, KEY_EVENT_BACK)
    }

    private fun pressPowerButton(adbDevice: AdbDevice) {
        Adb.pressKeyBlocking(adbDevice, KEY_EVENT_POWER)
    }

    fun pressBackspaceButton(adbDevice: AdbDevice) {
        Adb.pressKeyBlocking(adbDevice, KEY_EVENT_BACKSPACE)
    }

    fun turnDeviceScreenOn(adbDevice: AdbDevice) {
        while (!AdbDeviceProperties.isScreenOn(adbDevice)) pressPowerButton(adbDevice)
    }

    fun turnDeviceScreenOff(adbDevice: AdbDevice) {
        while (AdbDeviceProperties.isScreenOn(adbDevice)) pressPowerButton(adbDevice)
    }

    fun AdbDevice.tapUiNode(adbUiNode: AdbUiNode) {
        tapCoords(this, adbUiNode.centreX, adbUiNode.centreY)
    }

    fun tapCentre(adbDevice: AdbDevice, adbUiNode: AdbUiNode): String =
            adbUiNode.bounds?.let { bounds ->
                adbDevice.tapCentre(bounds).blockingSubscribe()
            }.toString()

    private fun AdbDevice.tapCentre(c: Array<Int>): Observable<String> =
            AdbProcesses.tapObservable(this,
                    UiHierarchyHelper.centreX(c),
                    UiHierarchyHelper.centreY(c))

    private fun tapCoords(adbDevice: AdbDevice, x: Int, y: Int): Boolean =
            AdbProcesses.tap(adbDevice, x, y).blockingLast("") != null

    private fun AdbDevice.tap(x: Int, y: Int): Observable<String> =
            AdbProcesses.tapObservable(this, x, y)
}
