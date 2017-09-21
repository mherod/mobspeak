package co.herod.adbwrapper

import co.herod.adbwrapper.AdbBusManager.uiNodeBus
import co.herod.adbwrapper.device.dumpsys
import co.herod.adbwrapper.device.windows
import co.herod.adbwrapper.model.*
import co.herod.adbwrapper.rx.FixedDurationTransformer
import co.herod.adbwrapper.rx.ResultChangeFixedDurationTransformer
import co.herod.adbwrapper.util.UiHierarchyHelper
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun startStreamingUiHierarchy(adbDevice: AdbDevice): Observable<UiNode> = Adb.dumpUiHierarchy(adbDevice, 30, TimeUnit.SECONDS)
        .map { it.substring(it.indexOf('<'), it.lastIndexOf('>') + 1) }
        .compose(ResultChangeFixedDurationTransformer())
        .map { AdbUiHierarchy(it, adbDevice) }
        .doOnEach(AdbBusManager.uiHierarchyBus)
        .map { it.xmlString }
        .compose { UiHierarchyHelper.uiXmlToNodes(it) }
        .map { UiNode(it) }
        .doOnEach(AdbBusManager.uiNodeBus)
        .observeOn(Schedulers.newThread())
        .subscribeOn(Schedulers.newThread())

fun fetchUiHierarchy(adbDevice: AdbDevice): Observable<UiNode> = Adb.dumpUiHierarchy(adbDevice, 30, TimeUnit.SECONDS)
        .map { it.substring(it.indexOf('<'), it.lastIndexOf('>') + 1) }
        .map { AdbUiHierarchy(it, adbDevice) }
        .map { it.xmlString }
        .compose { UiHierarchyHelper.uiXmlToNodes(it) }
        .map { UiNode(it) }
        .doOnEach(AdbBusManager.uiNodeBus)

private fun streamUiNodes() = streamUiNodeStringsInternal()
        .map { UiNode(it) }

fun AdbDevice.streamUiNodeStrings() = streamUiNodes()
        .map { it.toString() }

fun AdbDevice.streamUiNodes(packageIdentifier: String) =
        streamUiNodeStringsInternal()
                .filter { s -> UiHierarchyHelper.isPackage(packageIdentifier, s) }.map { UiNode(it) }

fun streamUiNodeStringsInternal() = uiNodeBus
        .map { it.toString() }
        .compose(FixedDurationTransformer(1, TimeUnit.DAYS))
        .onErrorReturn { throwable -> throwable.printStackTrace(); "" }
        .repeat()
        .filter { it.trim().isEmpty().not() }

fun AdbDevice.subscribeUiNodesSource(): Observable<UiNode> = Adb.dumpUiNodes(this)
        .compose(ResultChangeFixedDurationTransformer())
        .distinct { it.toString() }

fun AdbDevice.getWindowBounds(): UiBounds = dumpsys().windows().filterProperty("mBounds")
        .map { it.value }
        .map { it.substring(it.lastIndexOf('[') + 1, it.lastIndexOf(']')) }
        .map { it.split(',') }
        .map { it.map { Integer.parseInt(it) } }
        .map {
            val newList = ArrayList(it)
            if (newList.size == 2) {
                newList.add(0, 0)
                newList.add(0, 0)
            }
            newList
        }
        .map { UiBounds(it.toTypedArray()) }
        .blockingGet()