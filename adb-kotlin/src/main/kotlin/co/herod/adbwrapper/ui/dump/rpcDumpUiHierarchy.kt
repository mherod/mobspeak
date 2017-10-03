package co.herod.adbwrapper.ui.dump

import co.herod.adbwrapper.uiautomator.RpcBlah
import io.reactivex.Observable

fun rpcDumpUiHierarchy(): Observable<String> {
//    println("rpcDumpUiHierarchy")
    return RpcBlah.tryRpcUi().toObservable() // .doOnNext { println(it) }
}