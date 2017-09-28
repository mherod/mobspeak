@file:Suppress("unused")

package co.herod.adbwrapper

import co.herod.adbwrapper.AdbBusManager.uiNodeBus
import co.herod.adbwrapper.model.UiNode
import co.herod.adbwrapper.rx.FixedDurationTransformer
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

@JvmOverloads
fun streamUiNodes(packageIdentifier: String? = null): Observable<UiNode> =
        streamUiNodeStringsBus()
                .map { UiNode(it) }
                .filter { it.packageName == packageIdentifier }

fun streamUiNodeStrings(): Observable<String> =
        streamUiNodeStringsBus()
                .map { UiNode(it) }
                .map { it.toString() }

fun streamUiNodeStringsBus(): Observable<String> =
        uiNodeBus
                .map { it.toString() }
                .compose(FixedDurationTransformer(1, TimeUnit.DAYS))
                .onErrorReturn { throwable -> throwable.printStackTrace(); "" }
                .repeat()
                .filter { it.trim().isEmpty().not() }

