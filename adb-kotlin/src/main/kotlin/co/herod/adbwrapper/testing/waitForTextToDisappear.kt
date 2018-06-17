/*
 * Copyright (c) 2018. Herod
 */

package co.herod.adbwrapper.testing

import java.util.concurrent.TimeUnit

fun AdbDeviceTestHelper.waitForTextToDisappear(text: String) {

    try {
        waitForText(text, 10, TimeUnit.SECONDS)
    } catch (assertionError: AssertionError) {
        // ignore error if not found
    }
    waitSeconds(5)
    failOnText(text)
}