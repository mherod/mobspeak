@file:Suppress("unused")

package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbUiHierarchy
import co.herod.adbwrapper.model.UiNode
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

object AdbBusManager {

    val outputBus: BusSubject<String> = MainBusSubject()
    val uiHierarchyBus: BusSubject<AdbUiHierarchy> = AdbUiHierarchyBus()
    val uiNodeBus: BusSubject<UiNode> = AdbUiNodeBus()

    internal fun throttledBus(): Observable<String>? = outputBus.concatMap {
        Observable.timer(10, TimeUnit.MILLISECONDS)
                .flatMap { _ -> Observable.just(it) }
    }
}

class MainBusSubject : BusSubject<String>()
class AdbUiHierarchyBus : BusSubject<AdbUiHierarchy>()
class AdbUiNodeBus : BusSubject<UiNode>()
