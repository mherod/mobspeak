package co.herod.adbwrapper.ui

import co.herod.adbwrapper.exceptions.UiAutomatorBridgeUnavailableException
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiHierarchy
import co.herod.adbwrapper.ui.dump.compatDumpUiHierarchy
import co.herod.adbwrapper.ui.dump.fallbackDumpUiHierarchy
import co.herod.adbwrapper.ui.dump.primaryDumpUiHierarchy
import co.herod.adbwrapper.ui.dump.rpcDumpUiHierarchy
import co.herod.adbwrapper.uiautomator.uiAutomatorBridge
import co.herod.adbwrapper.util.isXmlOutput
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun AdbDevice.dumpUiHierarchy(
        timeout: Long = 30,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): Observable<UiHierarchy> = with(this) {
    Observable.just(this)
//            .doOnSubscribe { println("START $s3") }
//            .doOnNext { println("NEXT $s3") }
//            .doOnComplete { println("COMPLETE $s3") }
//            .doOnDispose { println("STOP $s3") }
            .flatMap {
                tryRpcDumpUiHierarchy()
                        .onErrorResumeNext { _: Throwable -> compatDumpUiHierarchy() }
                        .onErrorResumeNext { _: Throwable -> primaryDumpUiHierarchy() }
                        .onErrorResumeNext { _: Throwable -> fallbackDumpUiHierarchy() }
            }
            .retry()
            .observeOn(Schedulers.single())
            .subscribeOn(Schedulers.computation())
            .takeWhile { it.isNotBlank() && it.isXmlOutput() }
            .timeout(maxOf(10, timeUnit.toSeconds(timeout) / 5), TimeUnit.SECONDS)
            .retry()
            .timeout(timeout, timeUnit)
            .map { "${it.substringAfter('<').substringBeforeLast('>')}>" }
            .map { UiHierarchy(this, it) }
}

private fun AdbDevice.tryRpcDumpUiHierarchy(): Observable<String> = when {
    uiAutomatorBridge().blockingFirst() == true -> rpcDumpUiHierarchy()
    else -> Observable.error(UiAutomatorBridgeUnavailableException())
}

