package co.herod.adbwrapper.ui

import co.herod.adbwrapper.AdbBusManager
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiNode
import co.herod.adbwrapper.rx.ResultChangeFixedDurationTransformer
import co.herod.adbwrapper.util.UiHelper
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun AdbDevice.streamUi(): Observable<UiNode> =
        this.dumpUiHierarchy(30, TimeUnit.SECONDS)
                .compose(ResultChangeFixedDurationTransformer())
                .doOnEach(AdbBusManager._uiHierarchyBus)
                .map { it.xmlString }
                .compose { UiHelper.uiXmlToNodes(it) }
                .doOnEach(AdbBusManager._uiNodeBus)
                .doOnSubscribe {
                    println("Subscribe of streamUi")
                    AdbBusManager.uiHierarchyBusActive = true
                }
                .doOnDispose {
                    println("Dispose of streamUi")
                    AdbBusManager.uiHierarchyBusActive = false
                }