package co.herod.adbwrapper.ui

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.AdbUiHierarchy
import co.herod.adbwrapper.rx.ResultChangeFixedDurationTransformer
import co.herod.adbwrapper.ui.dumpUiHierarchy
import io.reactivex.Observable

fun AdbDevice.subscribeUiHierarchySource(): Observable<AdbUiHierarchy> =
        dumpUiHierarchy(this)
                .compose(ResultChangeFixedDurationTransformer())
                .distinct { it.toString() }