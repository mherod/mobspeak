package co.herod.adbwrapper.ui

import co.herod.adbwrapper.exceptions.UiAutomatorBridgeUnavailableException
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.ui.dump.rpcDumpUiHierarchy
import co.herod.adbwrapper.ui.uiautomator.uiAutomatorBridge
import io.reactivex.Observable

fun AdbDevice.tryRpcDumpUiHierarchy(): Observable<String> = when {
    uiAutomatorBridge().blockingFirst() == true -> rpcDumpUiHierarchy()
    else -> Observable.error(UiAutomatorBridgeUnavailableException())
}