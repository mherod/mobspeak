package co.herod.adbwrapper.ui

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiHierarchy
import co.herod.rx.ResultChangeFixedDurationTransformer
import io.reactivex.Observable

fun AdbDevice.subscribeUiHierarchySource(): Observable<UiHierarchy> =
        dumpUiHierarchy()
                .compose(ResultChangeFixedDurationTransformer())
//                .doOnSubscribe { println("subscribeUiSource $it") }