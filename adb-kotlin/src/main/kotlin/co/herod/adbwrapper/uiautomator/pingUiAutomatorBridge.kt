package co.herod.adbwrapper.uiautomator

import java.util.concurrent.TimeUnit

fun pingUiAutomatorBridge(rpcSession: RpcSession = RpcSession(9008)): Boolean =
        RpcSession.ping(rpcSession).timeout(2, TimeUnit.SECONDS).map { "pong" in it }.blockingGet()