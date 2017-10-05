package co.herod.adbwrapper.uiautomator

import co.herod.adbwrapper.device.rpcSession
import co.herod.adbwrapper.model.AdbDevice
import java.util.concurrent.TimeUnit

fun AdbDevice.pingUiAutomatorBridge(myRpcSession: RpcSession = rpcSession()): Boolean =
        RpcSession.ping(myRpcSession)
                .map { "pong" in it }
                .timeout(2, TimeUnit.SECONDS)
                .doOnError { println("pingUiAutomatorBridge error $it") }
                .onErrorReturn { false }
                .blockingGet()