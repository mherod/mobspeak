/*
 * Copyright (c) 2018. Herod
 */

package co.herod.adbwrapper.ui

import co.herod.adbwrapper.AdbBusManager
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiNode
import co.herod.kotlin.log
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

private val s = "streamUi"

fun AdbDevice.streamUi(): Observable<UiNode> {
    return Observable.timer(100, TimeUnit.MILLISECONDS)
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.newThread())
            .flatMap { dumpUiHierarchy() }
            .repeat()
            .doOnEach(myUiHierarchyBus)
            .flatMapIterable { it.uiNodes }
            .doOnEach(AdbBusManager._uiNodeBus)
            .doOnSubscribe {
                log("Subscribe of $s"); AdbBusManager.uiHierarchyBusActive = true
            }
            .doOnDispose {
                log("Dispose of $s"); AdbBusManager.uiHierarchyBusActive = false
            }
}

