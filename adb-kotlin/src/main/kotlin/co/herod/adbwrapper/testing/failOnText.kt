package co.herod.adbwrapper.testing

import co.herod.adbwrapper.model.UiNode
import co.herod.kotlin.ext.containsIgnoreCase
import co.herod.kotlin.ext.timeout
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
    val deadlineMillis = timeUnit.toMillis(timeout.toLong())
    val timeMillis = measureTimeMillis {
        // TEMP FIX to allow time for UI to refresh
        Observable.timer(2000, TimeUnit.MILLISECONDS)
        // Observable.timer(100, TimeUnit.MILLISECONDS)
                .flatMap { uiNodeSource() }
                .timeout(timeout, timeUnit)
                .onErrorResumeNext { _: Throwable -> Observable.empty() }
                .blockingForEach { uiNode: UiNode ->
                    if (uiNode.text.containsIgnoreCase(text)) {
                        throw AssertionError("Text was visible: $text")
                    }
                }
    }
    if ((deadlineMillis * 1.5) < timeMillis) {
        throw AssertionError("Exceeded provided timeout (took $timeMillis, expected $deadlineMillis)")
    }

    // TODO this needs to assess the uiHierarchy completely
}