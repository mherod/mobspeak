package co.herod.adbwrapper.testing

import co.herod.adbwrapper.Adb
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.AdbUiNode
import co.herod.adbwrapper.rx.ResultChangeFixedDurationTransformer
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by matthewherod on 04/09/2017.
 */

object AdbTestHelper {

    fun AdbDevice.failOnText(text: String, timeout: Long, timeUnit: TimeUnit) {

        Adb.dumpUiNodes(this)
                .compose(ResultChangeFixedDurationTransformer())
                .timeout(timeout, timeUnit)
                .onErrorResumeNext(Observable.empty<AdbUiNode>())
                .blockingForEach { adbUiNode ->
                    if (text.toLowerCase() in adbUiNode.text.toLowerCase()) {
                        throw AssertionFailedError("Text was visible")
                    }
                }
    }


    fun AdbDevice.waitForText(text: String, timeout: Long, timeUnit: TimeUnit) {

        try {
            Adb.dumpUiNodes(this)
                    .filter { text in it.text }
                    .timeout(timeout, timeUnit)
                    .blockingForEach { throw BlockingBreakerThrowable() }
        } catch (e: BlockingBreakerThrowable) {
            // good!
        }
    }
}

class BlockingBreakerThrowable : RuntimeException()
