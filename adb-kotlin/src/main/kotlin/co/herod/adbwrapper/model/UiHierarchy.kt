@file:Suppress("MemberVisibilityCanPrivate", "unused")

package co.herod.adbwrapper.model

import co.herod.adbwrapper.util.UiHelper
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.*

class UiHierarchy(
        val xmlString: String,
        val adbDevice: AdbDevice?,
        val dumpDate: Date = Date()
) {
//    init {
//        println("new uiHierarchy $dumpDate")
//    }

    val childUiNodes: MutableList<UiNode> by lazy {
        UiHelper.uiXmlToNodes(Observable.just(xmlString), dumpDate)
                .observeOn(Schedulers.computation())
                .filter { Objects.nonNull(it) }
                .toList()
                .blockingGet()
    }

    override fun toString(): String {
        return "UiHierarchy(xmlString='$xmlString', adbDevice=$adbDevice, dumpDate=$dumpDate)"
    }
}
