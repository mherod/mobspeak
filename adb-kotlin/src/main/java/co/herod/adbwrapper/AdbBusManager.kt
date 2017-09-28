@file:Suppress("unused", "ObjectPropertyName", "MemberVisibilityCanPrivate")

package co.herod.adbwrapper

import co.herod.adbwrapper.model.UiHierarchy
import co.herod.adbwrapper.model.UiNode
import co.herod.adbwrapper.rx.BusSubject
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

object AdbBusManager {

    internal val _outputBus: BusSubject<String> = MainBusSubject()
    val outputBus: BusSubject<String>
        get() = _outputBus

    var uiHierarchyBusActive = false

    internal val _uiHierarchyBus = BehaviorSubject.create<UiHierarchy>()

    val uiHierarchyBus: Observable<UiHierarchy>
        get() = _uiHierarchyBus
                .doOnSubscribe { }
                .doOnDispose { }

    internal val _uiNodeBus: BusSubject<UiNode> = AdbUiNodeBus()
    val uiNodeBus: Observable<UiNode>
        get() = _uiNodeBus

    internal fun throttledBus(): Observable<String>? = outputBus.concatMap {
        Observable.timer(10, TimeUnit.MILLISECONDS)
                .flatMap { _ -> Observable.just(it) }
    }
}

class MainBusSubject : BusSubject<String>()
// class AdbUiHierarchyBus : BusSubject<UiHierarchy>()
class AdbUiNodeBus : BusSubject<UiNode>()
