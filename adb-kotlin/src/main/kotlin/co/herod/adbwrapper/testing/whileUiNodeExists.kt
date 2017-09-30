@file:Suppress("unused")

package co.herod.adbwrapper.testing

import co.herod.adbwrapper.model.UiNode

fun AdbDeviceTestHelper.whileUiNodeExists(predicate: (UiNode) -> Boolean) = whileUiHierarchy { it.childUiNodes.any { predicate(it) } }