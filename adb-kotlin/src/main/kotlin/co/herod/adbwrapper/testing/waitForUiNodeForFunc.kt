package co.herod.adbwrapper.testing

import co.herod.adbwrapper.model.UiNode
import co.herod.adbwrapper.ui.sourceUiNodes
import co.herod.kotlin.ext.blocking
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun AdbDeviceTestHelper.waitForUiNodeForFunc(
        predicate: (UiNode) -> Boolean?,
        function: (UiNode) -> String? = { "Matched: ${it.resourceId}" },
        timeout: Int = 10,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): String = with(adbDevice) {

    Observable.timer(20, TimeUnit.MILLISECONDS)
            .flatMap {
                sourceUiNodes()
                        .buffer(50, TimeUnit.MILLISECONDS)
                        .flatMapIterable { it }
            }
            .filter { it.visible }
            .filter { predicate(it) == true } // filter for items passing predicate
            .firstOrError() // if not found in stream it will error
            .retry(1) // retry on error (stream finish before we match)
            .map { function(it).orEmpty() } // do this function with item
//            .doOnSuccess {
//                when {
//                    it.trim().startsWith("Matched:") -> println("\t\t$it")
//                }
//            }
            .blocking(timeout, timeUnit) // with max max timeout
}