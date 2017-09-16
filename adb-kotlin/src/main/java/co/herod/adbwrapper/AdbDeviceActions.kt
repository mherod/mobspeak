package co.herod.adbwrapper

import co.herod.adbwrapper.AdbProcesses.tap
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiBounds
import co.herod.adbwrapper.model.UiNode
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit.SECONDS

fun AdbDevice.tap(uiNode: UiNode): String =
        uiNode.bounds.let { bounds -> tap(bounds) }.toString()

fun AdbDevice.tap(c: UiBounds): String =
        tap(this, c.centreX, c.centreY).blocking()

fun AdbDevice.swipe(x1: Int, y1: Int, x2: Int, y2: Int, speed: Int = 500): String =
        AdbProcesses.swipe(this, x1, y1, x2, y2, speed).blocking()

fun AdbDevice.typeText(inputText: String): String =
        AdbProcesses.inputText(this, inputText).blocking()


fun Single<String>.blocking(): String = this
        .retry()
        .timeout(2, SECONDS)
        .onErrorReturn { "" }
        .blockingGet()

fun Observable<String>.blocking(): String = this
        .retry()
        .timeout(2, SECONDS)
        .last("")
        .blockingGet()