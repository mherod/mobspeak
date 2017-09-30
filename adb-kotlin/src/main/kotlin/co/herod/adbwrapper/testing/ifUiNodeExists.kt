@file:Suppress("unused")

package co.herod.adbwrapper.testing

import co.herod.adbwrapper.model.UiNode

fun AdbDeviceTestHelper.ifUiNodeExists(predicate: (UiNode) -> Boolean): Boolean = ifUiHierarchy { it.childUiNodes.any { predicate(it) } }