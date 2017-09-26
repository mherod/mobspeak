package co.herod.adbwrapper.testing

import co.herod.adbwrapper.model.UiNode
import co.herod.kotlin.ext.blocking
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun AdbDeviceTestHelper.waitForUiNodeForFunc(
        predicate: (UiNode) -> Boolean?,
        function: (UiNode) -> String? = { "Matched: ${it.resourceId}" },
        timeout: Int = 30,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): String = with(adbDevice) {

    Observable.timer(50, TimeUnit.MILLISECONDS)
            .flatMap {
                uiHierarchySource()
                        .buffer(250, TimeUnit.MILLISECONDS)
                        .flatMapIterable { it }
            }
            .filter { predicate(it) == true && it.visible } // filter for items passing predicate
            .firstOrError() // if not found in stream it will error
            .retry() // retry on error (stream finish before we match)
            .map { function(it).orEmpty() } // do this function with item
            .doOnSuccess {
                when {
                    it.startsWith("Matched:") -> println(it)
                }
            }
            .blocking(timeout, timeUnit) // with max max timeout
}