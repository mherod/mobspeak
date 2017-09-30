package co.herod.adbwrapper.testing

import co.herod.adbwrapper.device.tap
import co.herod.kotlin.ext.containsIgnoreCase
import java.util.concurrent.TimeUnit

fun AdbDeviceTestHelper.touchText(text: String) =
        with(adbDevice) {
            waitForUiNodeForFunc(
                    predicate = { it.text.containsIgnoreCase(text) },
                    function = { tap(it) },
                    timeout = 10,
                    timeUnit = TimeUnit.SECONDS
            )
        }