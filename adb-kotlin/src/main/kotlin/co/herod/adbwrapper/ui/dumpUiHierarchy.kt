package co.herod.adbwrapper.ui

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiHierarchy
import co.herod.adbwrapper.ui.dump.compatDumpUiHierarchy
import co.herod.adbwrapper.ui.dump.fallbackDumpUiHierarchy
import co.herod.adbwrapper.ui.dump.primaryDumpUiHierarchy
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun AdbDevice.dumpUiHierarchy(
        timeout: Long = 30,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): Observable<UiHierarchy> = with(this) {
    Observable.just(this)
//            .doOnSubscribe { println("START dumpUiHierarchy") }
//            .doOnNext { println("NEXT dumpUiHierarchy") }
//            .doOnComplete { println("COMPLETE dumpUiHierarchy") }
//            .doOnDispose { println("STOP dumpUiHierarchy") }
            .flatMap {
                when {
                    it.preferredUiAutomatorStrategy == 0 ->
                        this.compatDumpUiHierarchy()
                                .timeout(6, TimeUnit.SECONDS)
                    it.preferredUiAutomatorStrategy == 1 ->
                        this.primaryDumpUiHierarchy()
                                .timeout(6, TimeUnit.SECONDS)
                    it.preferredUiAutomatorStrategy == 2 ->
                        this.fallbackDumpUiHierarchy()
                                .timeout(6, TimeUnit.SECONDS)
                    else ->
                        this.compatDumpUiHierarchy()
                                .timeout(6, TimeUnit.SECONDS)
                }
            }
            .observeOn(Schedulers.single())
            .subscribeOn(Schedulers.computation())
            .filter { it.isNotBlank() }
            .timeout(10, TimeUnit.SECONDS)
            .retry()
            .timeout(timeout, timeUnit)
            .map {
                it.substring(
                        it.indexOf('<'),
                        it.lastIndexOf('>') + 1
                )
            }
            .map { UiHierarchy(it, this) }
}