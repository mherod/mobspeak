/*
 * Copyright (c) 2018. Herod
 */

package co.herod.adbwrapper.testing

import co.herod.adbwrapper.model.UiNode
import co.herod.adbwrapper.ui.screenshots.capture

fun AdbDeviceTestHelper.captureUiNode(predicate: (UiNode) -> Boolean) = with(adbDevice) {

    sampleUiHierarchy()
            .flatMapIterable { it.uiNodes }
            .filter { predicate(it) }
            .doOnNext { it.capture() }
            .blockingSubscribe()
}