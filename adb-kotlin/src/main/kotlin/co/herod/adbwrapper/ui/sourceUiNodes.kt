/*
 * Copyright (c) 2018. Herod
 */

package co.herod.adbwrapper.ui

import co.herod.adbwrapper.model.UiNode
import co.herod.adbwrapper.testing.AdbDeviceTestHelper
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

fun AdbDeviceTestHelper.sourceUiNodes(): Observable<UiNode> = with(adbDevice) {
    sourceUiHierarchy()
            .flatMapIterable { it.uiNodes }
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.computation())
            .retry(1)
}