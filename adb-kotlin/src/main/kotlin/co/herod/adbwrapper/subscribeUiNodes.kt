package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.AdbUiNode
import co.herod.adbwrapper.rx.ResultChangeFixedDurationTransformer
import io.reactivex.Observable

fun AdbDevice.subscribeUiNodes(): Observable<AdbUiNode> = Adb.dumpUiNodes(this)
        .compose(ResultChangeFixedDurationTransformer())
        .distinct { it.toString() }