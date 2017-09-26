package co.herod.adbwrapper.testing

import co.herod.adbwrapper.model.UiNode
import co.herod.kotlin.ext.containsIgnoreCase
import co.herod.kotlin.ext.timeout
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

@JvmOverloads
fun AdbDeviceTestHelper.failOnText(
        text: String,
        timeout: Int = 20,
        timeUnit: TimeUnit = TimeUnit.SECONDS
) = with(adbDevice) {

    Observable.timer(50, TimeUnit.MILLISECONDS)
            .flatMap {
                uiHierarchySource().
                        sample(1, TimeUnit.SECONDS)
            }
            .timeout(timeout, timeUnit)
            .onErrorResumeNext { _: Throwable -> Observable.empty() }
            .blockingForEach { uiNode: UiNode ->
                if (uiNode.text.containsIgnoreCase(text)) {
                    throw AssertionError("Text was visible: $text")
                }
            }
}