package co.herod.adbwrapper.testing

import co.herod.kotlin.ext.timeout
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@JvmOverloads
fun AdbDeviceTestHelper.waitForActivity(
        activityName: String,
        timeout: Int = 30,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): Boolean = with(adbDevice) {
    try {
        Observable.timer(100, TimeUnit.MILLISECONDS)
                .flatMap {
                    Observable.fromCallable {
                        matchActivity(activityName, 10, TimeUnit.SECONDS)
                    }
                }
                .filter { it }
                .firstOrError()
                .retry()
                .toObservable()
                .timeout(timeout, timeUnit)
                .blockingFirst()
    } catch (timeoutException: TimeoutException) {
        throw AssertionError("Timed out when waiting for activity")
    }
}