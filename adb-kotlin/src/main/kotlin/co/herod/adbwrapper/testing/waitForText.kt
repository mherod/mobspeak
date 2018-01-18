/*
 * Copyright (c) 2018. Herod
 */

package co.herod.adbwrapper.testing

import co.herod.kotlin.ext.containsIgnoreCase
import java.util.concurrent.TimeUnit

@JvmOverloads
fun AdbDeviceTestHelper.waitForText(
        text: String,
        timeout: Int = 10,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): String =
        with(adbDevice) {
            waitForUiNodeForFunc(
                    { uiNode -> uiNode.text.containsIgnoreCase(text) },
                    timeout = timeout,
                    timeUnit = timeUnit
            )
        }