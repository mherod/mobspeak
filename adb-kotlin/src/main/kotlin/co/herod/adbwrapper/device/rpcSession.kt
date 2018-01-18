/*
 * Copyright (c) 2018. Herod
 */

package co.herod.adbwrapper.device

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.ui.uiautomator.RpcSession

fun AdbDevice.rpcSession(): RpcSession = RpcSession(port = rpcPort)