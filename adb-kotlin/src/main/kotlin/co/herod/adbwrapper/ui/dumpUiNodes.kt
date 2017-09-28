package co.herod.adbwrapper.ui

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiNode
import co.herod.adbwrapper.ui.dumpUiHierarchy
import co.herod.adbwrapper.util.UiHelper
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit

fun dumpUiNodes(
        adbDevice: AdbDevice,
        timeout: Long = 30,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): Observable<UiNode> =
        dumpUiHierarchy(adbDevice, timeout, timeUnit)
                .map { it.xmlString }
                .distinct { it }
                .compose { UiHelper.uiXmlToNodes(it) }
                .filter { Objects.nonNull(it) }