package co.herod.adbwrapper.ui

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiNode
import co.herod.adbwrapper.util.UiHelper
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit

fun AdbDevice.dumpUiNodes(
        timeout: Long = 30,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): Observable<UiNode> =
        this.dumpUiHierarchy(timeout, timeUnit)
                .map { it.xmlString }
                .distinct { it }
                .compose { UiHelper.uiXmlToNodes(it) }
                .filter { Objects.nonNull(it) }