/*
 * Copyright (c) 2018. Herod
 */

package co.herod.adbwrapper.ui

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiNode
import io.reactivex.Observable

fun AdbDevice.dumpUiNodes(): Observable<UiNode> =
        dumpUiHierarchy().flatMapIterable { it.uiNodes }