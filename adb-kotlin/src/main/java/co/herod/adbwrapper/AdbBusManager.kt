@file:Suppress("unused", "ObjectPropertyName", "MemberVisibilityCanPrivate")

package co.herod.adbwrapper

import co.herod.adbwrapper.bus.BusSubject
import co.herod.adbwrapper.bus.UiNodeBus
import co.herod.adbwrapper.model.UiNode
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

object AdbBusManager {

    internal val _outputBus: BusSubject<String> = MainBusSubject()
    val outputBus: BusSubject<String>
        get() = _outputBus

    var uiAutomatorBridgeActive: Boolean = false

    var uiHierarchyBusActive: Boolean = false

    internal val _uiNodeBus: BusSubject<UiNode> = UiNodeBus()

    @JvmStatic
    val uiNodeBus: Observable<UiNode>
        get() = _uiNodeBus

    internal fun throttledBus(): Observable<String>? = outputBus.concatMap {
        Observable.timer(20, TimeUnit.MILLISECONDS)
                .flatMap { t -> Observable.just(it) }
    }
}

class MainBusSubject : BusSubject<String>()
