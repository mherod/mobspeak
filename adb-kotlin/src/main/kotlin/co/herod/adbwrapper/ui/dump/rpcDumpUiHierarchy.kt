package co.herod.adbwrapper.ui.dump

import co.herod.adbwrapper.uiautomator.RpcBlah
import io.reactivex.Observable

fun rpcDumpUiHierarchy(): Observable<String> {

    // if not started rpc then error

//    println("rpcDumpUiHierarchy")
    return RpcBlah.tryRpcUi().toObservable() // .doOnNext { println(it) }
}