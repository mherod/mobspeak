package co.herod.adbwrapper.ui.uiautomator

import co.herod.adbwrapper.device.rpcSession
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.ui.Blah.Companion.subject
import java.util.concurrent.TimeUnit

fun AdbDevice.pingUiAutomatorBridge(myRpcSession: RpcSession = rpcSession()): Boolean =
        RpcSession.ping(myRpcSession)
                .map { println(it); it }
                .map { "pong" in it }
                .map {
                    when {
                        it -> dumpWindowHierarchyCheck(myRpcSession)
                        else -> false
                    }
                }
                .timeout(3, TimeUnit.SECONDS)
                .doOnError { println("pingUiAutomatorBridge error $it") }
                .onErrorReturn { false }
                .doOnSuccess { println("pingUiAutomatorBridge $it") }
                .doOnSuccess { subject.onNext(it) }
                .blockingGet()

private fun dumpWindowHierarchyCheck(myRpcSession: RpcSession): Boolean {
    return RpcSession.dumpWindowHierarchy(myRpcSession)
            .map { "bounds" in it }
            .onErrorReturn { false }
            .blockingGet()
}