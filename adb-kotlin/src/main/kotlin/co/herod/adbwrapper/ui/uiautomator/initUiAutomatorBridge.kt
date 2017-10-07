package co.herod.adbwrapper.ui.uiautomator

import co.herod.adbwrapper.S
import co.herod.adbwrapper.command
import co.herod.adbwrapper.device.processes.murder
import co.herod.adbwrapper.environment.forwardPort
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.processes.spotAdbError
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun AdbDevice.initUiAutomatorBridge() {

    if (pingUiAutomatorBridge()) {
        return
    }

    murder(S.PACKAGE_UIAUTOMATOR_TEST).blockingSubscribe({}, {})

    murder(S.PACKAGE_UIAUTOMATOR).blockingSubscribe({}, {})

    Observable.timer(10, TimeUnit.MILLISECONDS)
            .flatMap { command(instrumentationCommand) }
            .observeOn(Schedulers.single())
            .flatMap { spotAdbError(it) }
            .retry(1)
            .doOnNext {
                forwardPort(local = rpcPort)
                println("online: ${pingUiAutomatorBridge()}")
            }
            .takeUntil { "INSTRUMENTATION_" in it }
            .subscribe({ println(it) }, { println("errorrr $it") })
}