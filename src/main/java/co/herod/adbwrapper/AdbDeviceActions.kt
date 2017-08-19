package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.AdbUiNode
import co.herod.adbwrapper.util.UiHierarchyHelper
import io.reactivex.Completable
import java.util.concurrent.TimeUnit

object AdbDeviceActions {

    private const val KEY_EVENT_HOME = 3
    private const val KEY_EVENT_BACK = 4
    private const val KEY_EVENT_POWER = 26
    private const val KEY_EVENT_BACKSPACE = 67

    fun AdbDevice?.pressHomeButton() {
        Adb.pressKeyBlocking(this, KEY_EVENT_HOME)
    }

    fun AdbDevice?.pressBackButton() {
        Adb.pressKeyBlocking(this, KEY_EVENT_BACK)
    }

    fun AdbDevice?.pressPowerButton() {
        Adb.pressKeyBlocking(this, KEY_EVENT_POWER)
    }

    fun AdbDevice?.pressBackspaceButton() {
        Adb.pressKeyBlocking(this, KEY_EVENT_BACKSPACE)
    }

    fun turnDeviceScreenOn(adbDevice: AdbDevice?) {
        while (!AdbDeviceProperties.isScreenOn(adbDevice)) adbDevice.pressPowerButton()
    }

    fun turnDeviceScreenOff(adbDevice: AdbDevice?) {
        while (AdbDeviceProperties.isScreenOn(adbDevice)) adbDevice.pressPowerButton()
    }

    fun AdbDevice.tapCentre(adbUiNode: AdbUiNode) = this.tapCentre(adbUiNode.bounds!!)

    private fun AdbDevice.tapCentre(c: Array<Int>) = ProcessHelper.blocking(AdbProcesses.tap(this,
            UiHierarchyHelper.centreX(c),
            UiHierarchyHelper.centreY(c)))

    fun AdbDevice.tapUiNode(s: AdbUiNode) {
        UiHierarchyHelper.extractBoundsInts(s)?.subscribe { tapCentre(it) }
    }

    fun tapCoords(adbDevice: AdbDevice, x: Int, y: Int): Boolean =
            Completable.fromObservable(ProcessHelper.observableProcess(AdbProcesses.tap(adbDevice, x, y)))
                    .blockingAwait(5, TimeUnit.SECONDS)
}
