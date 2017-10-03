package co.herod.adbwrapper.ui

import co.herod.adbwrapper.AdbBusManager
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiNode
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun AdbDevice.streamUi(): Observable<UiNode> =
        Observable.timer(100, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread())
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