package co.herod.adbwrapper.ui

import co.herod.adbwrapper.model.UiNode
import co.herod.adbwrapper.testing.AdbDeviceTestHelper
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun AdbDeviceTestHelper.sourceUiNodes(): Observable<UiNode> = with(adbDevice) {

    Observable.timer(5, TimeUnit.MILLISECONDS)
            .flatMap { sourceUiHierarchy().flatMapIterable { it.childUiNodes } }
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.computation())
}