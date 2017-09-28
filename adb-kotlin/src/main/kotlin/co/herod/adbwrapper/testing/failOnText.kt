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
    val deadlineMillis = timeUnit.toMillis(timeout.toLong())
    val timeMillis = measureTimeMillis {
        Observable.timer(5, TimeUnit.MILLISECONDS)
                .flatMap { uiNodeSource() }
                .timeout(timeout, timeUnit)
                .onErrorResumeNext { _: Throwable -> Observable.empty() }
                .blockingForEach { uiNode: UiNode ->
                    if (uiNode.text.containsIgnoreCase(text)) {
                        throw AssertionError("Text was visible: $text")
                    }
                }
    }
    if ((deadlineMillis * 1.1) < timeMillis) {
        throw AssertionError("Exceeded provided timeout (took $timeMillis, expected $deadlineMillis)")
    }
}