package co.herod.adbwrapper.testing

import co.herod.adbwrapper.model.UiHierarchy

fun AdbDeviceTestHelper.whileUiHierarchy(predicate: (UiHierarchy) -> Boolean?) = with(adbDevice) {
    sampleUiHierarchy()
            .takeWhile { predicate(it) == true }
            .blockingSubscribe({
                println("Blocking for uiHierarchy condition")
            }, {})
}