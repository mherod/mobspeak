package co.herod.adbwrapper.device

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.uiautomator.RpcSession

fun AdbDevice.rpcSession(): RpcSession = RpcSession(port = rpcPort)