package co.herod.adbwrapper.uiautomator

fun pingUiAutomatorBridge(rpcSession: RpcSession = RpcSession(9008)) =
        RpcSession.ping(rpcSession).map { "pong" in it }.blockingGet()