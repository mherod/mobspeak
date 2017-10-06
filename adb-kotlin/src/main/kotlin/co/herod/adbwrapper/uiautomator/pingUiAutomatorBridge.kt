package co.herod.adbwrapper.uiautomator

import co.herod.adbwrapper.device.rpcSession
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.ui.Blah.Companion.subject
import java.util.concurrent.TimeUnit

fun AdbDevice.pingUiAutomatorBridge(myRpcSession: RpcSession = rpcSession()): Boolean =
        RpcSession.ping(myRpcSession)
                .map { "pong" in it }
                .timeout(3, TimeUnit.SECONDS)
                .doOnError { println("pingUiAutomatorBridge error $it") }
                .onErrorReturn { false }
                .doOnSuccess { println("pingUiAutomatorBridge $it") }
                .doOnSuccess { subject.onNext(it) }
                .blockingGet()