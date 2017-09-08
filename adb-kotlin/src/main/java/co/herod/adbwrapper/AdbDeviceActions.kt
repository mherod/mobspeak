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

    fun pressHomeButton(adbDevice: AdbDevice?) {
        Adb.pressKeyBlocking(adbDevice, KEY_EVENT_HOME)
    }

    fun pressBackButton(adbDevice: AdbDevice?) {
        Adb.pressKeyBlocking(adbDevice, KEY_EVENT_BACK)
    }

    fun pressPowerButton(adbDevice: AdbDevice?) {
        Adb.pressKeyBlocking(adbDevice, KEY_EVENT_POWER)
    }

    fun pressBackspaceButton(adbDevice: AdbDevice?) {
        Adb.pressKeyBlocking(adbDevice, KEY_EVENT_BACKSPACE)
    }

    fun turnDeviceScreenOn(adbDevice: AdbDevice?) {
        while (!AdbDeviceProperties.isScreenOn(adbDevice)) pressPowerButton(adbDevice)
    }

    fun turnDeviceScreenOff(adbDevice: AdbDevice?) {
        while (AdbDeviceProperties.isScreenOn(adbDevice)) pressPowerButton(adbDevice)
    }

    fun tapCentre(adbDevice: AdbDevice, adbUiNode: AdbUiNode) = adbUiNode.bounds?.let { adbDevice.tapCentre(it) }

    private fun AdbDevice.tapCentre(c: Array<Int>) = Adb.processFactory.blocking(AdbProcesses.tap(this,
            UiHierarchyHelper.centreX(c),
            UiHierarchyHelper.centreY(c)))

    fun AdbDevice.tapUiNode(s: AdbUiNode) {
        UiHierarchyHelper.extractBoundsInts(s)?.subscribe { tapCentre(it) }
    }

    fun tapCoords(adbDevice: AdbDevice, x: Int, y: Int): Boolean =
            Completable.fromObservable(Adb.processFactory.observableProcess(AdbProcesses.tap(adbDevice, x, y)))
                    .blockingAwait(5, TimeUnit.SECONDS)
}
