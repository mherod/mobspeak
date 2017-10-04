package co.herod.adbwrapper.ui.dump

import co.herod.adbwrapper.exceptions.UiAutomatorBridgeUnavailableException
import co.herod.adbwrapper.ui.isUiAutomatorActive
import co.herod.adbwrapper.uiautomator.RpcBlah
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun rpcDumpUiHierarchy(): Observable<String> {

    // TODO observable processes for logcat

    if (isUiAutomatorActive().not()) {
        return Observable.error(UiAutomatorBridgeUnavailableException())
    }

    return RpcBlah.tryRpcUi()
            .toObservable()
//            .doOnSubscribe { println("Subscribe rpcDumpUiHierarchy") }
//            .doOnDispose { println("Dispose rpcDumpUiHierarchy") }
            .doOnNext { println(it.substringBefore("class")) }
            .timeout(1, TimeUnit.SECONDS)
}

