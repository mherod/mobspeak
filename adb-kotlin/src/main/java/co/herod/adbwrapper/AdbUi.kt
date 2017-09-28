@file:Suppress("unused")

package co.herod.adbwrapper

import co.herod.adbwrapper.AdbBusManager.uiHierarchyBusActive
import co.herod.adbwrapper.AdbBusManager.uiNodeBus
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.AdbUiHierarchy
import co.herod.adbwrapper.model.UiNode
import co.herod.adbwrapper.rx.FixedDurationTransformer
import co.herod.adbwrapper.rx.ResultChangeFixedDurationTransformer
import co.herod.adbwrapper.util.UiHelper
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun AdbDevice.streamUiHierarchy(): Observable<UiNode> =
        dumpUiHierarchy(this, 30, TimeUnit.SECONDS)
                .compose(ResultChangeFixedDurationTransformer())
                .doOnEach(AdbBusManager._uiHierarchyBus)
                .map { it.xmlString }
                .compose { UiHelper.uiXmlToNodes(it) }
                .doOnEach(AdbBusManager._uiNodeBus)
                .doOnSubscribe {
                    println("Subscribe of streamUiHierarchy")
                    uiHierarchyBusActive = true
                }
                .doOnDispose {
                    println("Dispose of streamUiHierarchy")
                    uiHierarchyBusActive = false
                }
// TODO do something on ui change

@JvmOverloads
fun streamUiNodes(packageIdentifier: String? = null): Observable<UiNode> =
        streamUiNodeStringsInternal()
                .map { UiNode(it) }
                .filter { it.packageName == packageIdentifier }

fun streamUiNodeStrings(): Observable<String> =
        streamUiNodeStringsInternal()
                .map { UiNode(it) }
                .map { it.toString() }

fun streamUiNodeStringsInternal(): Observable<String> =
        uiNodeBus
                .map { it.toString() }
                .compose(FixedDurationTransformer(1, TimeUnit.DAYS))
                .onErrorReturn { throwable -> throwable.printStackTrace(); "" }
                .repeat()
                .filter { it.trim().isEmpty().not() }

fun AdbDevice.subscribeUiNodesSource(): Observable<UiNode> =
        dumpUiNodes(this, 30, TimeUnit.SECONDS)
                .compose(ResultChangeFixedDurationTransformer())
                .distinct { it.toString() }

