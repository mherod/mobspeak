/*
 * Copyright (c) 2018. Herod
 */

@file:Suppress("unused")

package co.herod.adbwrapper.ui

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiNode
import co.herod.rx.ResultChangeFixedDurationTransformer
import io.reactivex.Observable

fun AdbDevice.subscribeUiNodesSource(): Observable<UiNode> =
        dumpUiNodes().compose(ResultChangeFixedDurationTransformer())