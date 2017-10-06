package co.herod.adbwrapper.ui

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiHierarchy
import co.herod.adbwrapper.ui.lock.putLock
import co.herod.adbwrapper.ui.lock.releaseLock
import co.herod.adbwrapper.ui.lock.waitForLockRelease
import co.herod.adbwrapper.util.isXmlOutput
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun AdbDevice.dumpUiHierarchy(
        timeout: Long = 30,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): Observable<UiHierarchy> = with(this) {
    waitForLockRelease()
            .flatMap { singleInternalDumpUiHierarchyAttempt() }
            .retry()
            // if it failed go again
            // repeat handled more above
            .doOnSubscribe { putLock() }
            .doOnDispose { releaseLock() }
            .observeOn(Schedulers.single())
            .subscribeOn(Schedulers.computation())
            .takeWhile { it.isNotBlank() && it.isXmlOutput() }
            // filter for good stuff
            .timeout(maxOf(10, timeUnit.toSeconds(timeout) / 5), TimeUnit.SECONDS)
            // taking too long OMG
            .retry()
            // we may have timed out
            .timeout(timeout, timeUnit)
            // hard deadline
            .map { extractXmlString(it) }
            // pull out the xml bit and then
            // BOOM!!
            .map { UiHierarchy(this, it) }
}

