package co.herod.adbwrapper.ui

import co.herod.adbwrapper.AdbBusManager
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiNode
import co.herod.adbwrapper.rx.ResultChangeFixedDurationTransformer
import io.reactivex.Observable

fun AdbDevice.streamUi(): Observable<UiNode> =
        dumpUiHierarchy()
                .compose(ResultChangeFixedDurationTransformer())
                .doOnEach(AdbBusManager._uiHierarchyBus)
                .flatMapIterable { it.childUiNodes }
                .doOnEach(AdbBusManager._uiNodeBus)
                .doOnSubscribe {
                    println("Subscribe of streamUi")
                    AdbBusManager.uiHierarchyBusActive = true
                }
                .doOnDispose {
                    println("Dispose of streamUi")
                    AdbBusManager.uiHierarchyBusActive = false
                }