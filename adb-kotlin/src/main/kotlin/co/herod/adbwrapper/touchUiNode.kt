package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.AdbUiNode

fun AdbDevice.touchUiNode(uiNode: AdbUiNode) {
    AdbDeviceActions.tapCentre(this, uiNode)
}