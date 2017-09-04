package co.herod.adbwrapper.testing

import co.herod.adbwrapper.AdbBusManager
import co.herod.adbwrapper.AdbUi
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.AdbUiNode
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by matthewherod on 04/09/2017.
 */

object AdbTestHelper {

    fun AdbDevice.failOnText(text: String) {

        AdbUi.startStreamingUiHierarchy(this).subscribe()

        AdbBusManager.getAdbUiNodeBus()
                .filter { Objects.nonNull(it) }
                .timeout(10, TimeUnit.SECONDS)
                .onErrorResumeNext(Observable.empty<AdbUiNode>())
                .blockingForEach { adbUiNode ->
                    if (adbUiNode.text.toLowerCase().contains(text.toLowerCase())) {
                        throw AssertionFailedError("Text was visible")
                    }
                }
    }


    fun AdbDevice.waitForText(text: String) {

        AdbUi.startStreamingUiHierarchy(this).subscribe()

        try {
            AdbBusManager.getAdbUiNodeBus()
                    .filter { adbUiNode -> adbUiNode.text.contains(text) }
                    .timeout(20, TimeUnit.SECONDS)
                    .blockingForEach { throw BlockingBreakerThrowable() }
        } catch (e: BlockingBreakerThrowable) {
            // good!
        }
    }
}


class BlockingBreakerThrowable : RuntimeException()
class AssertionFailedError(val s: String) : Throwable()
