package co.herod.adbwrapper.testing

import co.herod.adbwrapper.model.UiNode
import java.util.concurrent.TimeUnit

fun AdbDeviceTestHelper.waitForUiNode(
        predicate: (UiNode) -> Boolean
): String =
        waitForUiNodeForFunc(
                predicate = predicate,
                timeout = 10,
                timeUnit = TimeUnit.SECONDS
        )