/*
 * Copyright (c) 2018. Herod
 */

@file:Suppress("unused")

package co.herod.adbwrapper.testing

import co.herod.adbwrapper.model.UiHierarchy

@Deprecated(
        replaceWith = ReplaceWith("whileUiHierarchy"),
        message = "Method name has changed"
)
fun AdbDeviceTestHelper.waitWhileUiHierarchy(predicate: (UiHierarchy) -> Boolean?) = with(adbDevice) {
    whileUiHierarchy(predicate)
}

