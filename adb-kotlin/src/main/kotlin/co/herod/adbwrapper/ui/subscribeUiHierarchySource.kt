package co.herod.adbwrapper.ui

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiHierarchy
import co.herod.adbwrapper.rx.ResultChangeFixedDurationTransformer
import io.reactivex.Observable

fun AdbDevice.subscribeUiHierarchySource(): Observable<UiHierarchy> =
        this.dumpUiHierarchy()
                .compose(ResultChangeFixedDurationTransformer())