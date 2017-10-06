package co.herod.adbwrapper.ui.lock

import co.herod.adbwrapper.command
import co.herod.adbwrapper.model.AdbDevice
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun AdbDevice.waitForLockRelease(): Observable<String> =
        command(command = "shell cat $lockPath")
                .sample(50, TimeUnit.MILLISECONDS)
                .doOnNext { println("output from lockfile $it") }
                .takeUntil { s1 == it }
                .timeout(3, TimeUnit.SECONDS)
                .last("")
                .toObservable()