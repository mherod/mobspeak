/*
 * Copyright (c) 2018. Herod
 */

package co.herod.adbwrapper.testing

import co.herod.adbwrapper.device.input.tap
import co.herod.adbwrapper.model.AdbDevice
import co.herod.kotlin.ext.containsIgnoreCase
import java.util.concurrent.TimeUnit

fun AdbDeviceTestHelper.touchText(text: String) =
        touchTextInternal(text, adbDevice, 10, TimeUnit.SECONDS)

private fun AdbDeviceTestHelper.touchTextInternal(
        text: String,
        adbDevice1: AdbDevice,
        timeout: Int = 10,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): String =
        waitForUiNodeForFunc(
                predicate = { it.text.containsIgnoreCase(text) },
                function = adbDevice1::tap,
                timeout = timeout,
                timeUnit = timeUnit
        )