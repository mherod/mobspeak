package co.herod.adbwrapper.ui

import co.herod.adbwrapper.AdbBusManager
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiNode
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun AdbDevice.streamUi(): Observable<UiNode> =
        Observable.timer(600, TimeUnit.MILLISECONDS)
                .flatMap { dumpUiHierarchy() }
                .repeat()
                .doOnEach(AdbBusManager._uiHierarchyBus)
                .flatMapIterable { it.uiNodes }
                .doOnEach(AdbBusManager._uiNodeBus)
                .doOnSubscribe {
                    println("Subscribe of streamUi")
                    AdbBusManager.uiHierarchyBusActive = true
                }
                .doOnDispose {
                    println("Dispose of streamUi")
                    AdbBusManager.uiHierarchyBusActive = false
                }