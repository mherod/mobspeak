package co.herod.adbwrapper.testing

import co.herod.adbwrapper.device.input.tap
import co.herod.adbwrapper.model.UiNode
import java.util.concurrent.TimeUnit

fun AdbDeviceTestHelper.touchUiNode(predicate: (UiNode) -> Boolean) =
        with(adbDevice) {
            waitForUiNodeForFunc(
                    predicate = predicate,
                    function = { tap(it) },
                    timeout = 10,
                    timeUnit = TimeUnit.SECONDS
            )
        }