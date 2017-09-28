package co.herod.adbwrapper.testing

import co.herod.adbwrapper.AdbBusManager
import co.herod.adbwrapper.model.UiNode
import co.herod.adbwrapper.subscribeUiNodesSource
import co.herod.adbwrapper.util.UiHelper
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun AdbDeviceTestHelper.uiNodeSource(): Observable<UiNode> = with(adbDevice) {

    Observable.timer(5, TimeUnit.MILLISECONDS)
            .flatMap {
                if (AdbBusManager.uiHierarchyBusActive) {
                    AdbBusManager.uiHierarchyBus
                            .map { it.xmlString }
                            .compose { UiHelper.uiXmlToNodes(it) }
                } else {
                    subscribeUiNodesSource()
                }
            }
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.computation())
}