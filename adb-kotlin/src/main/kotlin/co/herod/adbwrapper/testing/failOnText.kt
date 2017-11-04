package co.herod.adbwrapper.testing

import co.herod.adbwrapper.model.UiNode
import co.herod.adbwrapper.ui.sourceUiNodes
import co.herod.kotlin.ext.containsIgnoreCase
import co.herod.kotlin.now
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

@JvmOverloads
fun AdbDeviceTestHelper.failOnText(
        text: String,
        timeout: Int = 20,
        timeUnit: TimeUnit = TimeUnit.SECONDS
) = with(adbDevice) {

    // Required cases:
    // fail - if text is visible throughout
    // fail - if text becomes visible
    // pass immediately - if text disappears before end of timeout
    // fail fast mode - fail immediately - if text is visible within the first second
    // pass fast mode - pass immediately - if text is not visible within the first second

    // requires at least ~?? second timeout
    val deadlineMillis = maxOf(timeUnit.toMillis(timeout.toLong()), 750)
    val timeMillis = measureTimeMillis {
        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .flatMap {
                    val startTime = now().plusMillis(100)
                    sourceUiNodes()
                            .skipWhile { it.time.isBefore(startTime) }
                            .skipWhile { it.text.containsIgnoreCase(text).not() }
                }
                .timeout(
                        deadlineMillis,
                        TimeUnit.MILLISECONDS
                )
                .onErrorResumeNext { _: Throwable -> Observable.empty() }
                .blockingForEach { uiNode: UiNode ->

                    val eachTime = uiNode.time.plusMillis(150)

                    if (uiNode.text.containsIgnoreCase(text) && eachTime.isAfter(now()) ) {
                        throw AssertionError("Text was visible: $text")
                    }
                }
    }
    if ((deadlineMillis * 1.5) < timeMillis) {
        throw AssertionError("Exceeded provided timeout (took $timeMillis, expected $deadlineMillis)")
    }

    // TODO this needs to assess the uiHierarchy completely
}
