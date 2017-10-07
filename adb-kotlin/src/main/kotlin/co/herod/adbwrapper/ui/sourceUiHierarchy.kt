@file:Suppress("unused")

package co.herod.adbwrapper.ui

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiHierarchy
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

fun AdbDevice.sourceUiHierarchy(): Observable<UiHierarchy> = Observable.just(this)
        .flatMap { it.myUiHierarchyBus }
        .observeOn(Schedulers.single())
        .subscribeOn(Schedulers.computation())
