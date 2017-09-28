package co.herod.adbwrapper.testing

import co.herod.adbwrapper.model.UiNode
import co.herod.adbwrapper.ui.sourceUiNodes
import java.util.concurrent.TimeUnit

fun AdbDeviceTestHelper.waitWhileUiNodeExists(predicate: (UiNode) -> Boolean?) = with(adbDevice) {

    sourceUiNodes()
            .filter { predicate(it) == true && it.visible }
            .doOnNext { println("Blocking while visible: $it") }
            .buffer(200, TimeUnit.MILLISECONDS)
            .doOnNext { list ->
                if (list.size > 0) {
                    println("Blocking on ${list.size} uiNodes")
                }
            }
            .takeWhile { it.isNotEmpty() }
            .timeout(30, TimeUnit.SECONDS)
            .blockingSubscribe()
}
