package co.herod.adbwrapper

import co.herod.adbwrapper.AdbBusManager.uiNodeBus
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.AdbUiHierarchy
import co.herod.adbwrapper.model.UiNode
import co.herod.adbwrapper.rx.FixedDurationTransformer
import co.herod.adbwrapper.rx.ResultChangeFixedDurationTransformer
import co.herod.adbwrapper.util.UiHierarchyHelper
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

object AdbUi {

//    fun startStreamingUiHierarchy(adbDevice: AdbDevice): Observable<UiNode> = Adb.dumpUiHierarchy(adbDevice, 30, TimeUnit.SECONDS)
//            .map { it.substring(it.indexOf('<'), it.lastIndexOf('>') + 1) }
//            .compose(ResultChangeFixedDurationTransformer())
//            // .doOnNext(s -> screenshotBlocking(adbDevice, true))
//            .map { AdbUiHierarchy(it, adbDevice) }
//            .doOnEach(AdbBusManager.getAdbUiHierarchyBus())
//            .map { it.xmlString }
//            .compose { UiHierarchyHelper.uiXmlToNodes(it) }
//            .map { UiNode(it) }
//            .doOnEach(AdbBusManager.getAdbUiNodeBus())
//            .observeOn(Schedulers.newThread())
//            .subscribeOn(Schedulers.newThread())

    fun fetchUiHierarchy(adbDevice: AdbDevice): Observable<UiNode> = Adb.dumpUiHierarchy(adbDevice, 30, TimeUnit.SECONDS)
            .map { it.substring(it.indexOf('<'), it.lastIndexOf('>') + 1) }
            .map { AdbUiHierarchy(it, adbDevice) }
            .map { it.xmlString }
            .compose { UiHierarchyHelper.uiXmlToNodes(it) }
            .map { UiNode(it) }
            .doOnEach(AdbBusManager.getAdbUiNodeBus())

    private fun streamUiNodes() = streamUiNodeStringsInternal().map { UiNode(it) }

    fun AdbDevice.streamUiNodeStrings() = streamUiNodes().map { it.toString() }

    fun AdbDevice.streamUiNodes(packageIdentifier: String) = streamUiNodeStrings(packageIdentifier).map { UiNode(it) }

    private fun streamUiNodeStrings(packageIdentifier: String) =
            streamUiNodeStringsInternal().filter { s -> UiHierarchyHelper.isPackage(packageIdentifier, s) }

    private fun streamUiNodeStringsInternal() = uiNodeBus
            .map { it.toString() }
            .compose(FixedDurationTransformer(1, TimeUnit.DAYS))
            .onErrorReturn { throwable -> throwable.printStackTrace(); "" }
            .repeat()
            .filter { it.trim().isEmpty().not() }

}

fun AdbDevice.subscribeUiNodesSource(): Observable<UiNode> = Adb.dumpUiNodes(this)
        .compose(ResultChangeFixedDurationTransformer())
        .distinct { it.toString() }