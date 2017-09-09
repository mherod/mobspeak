package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.util.PropHelper
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.Map.Entry

internal object AdbDeviceProperties {

    const val PROPS_DISPLAY = "display"
    const val PROPS_INPUT_METHOD = "input_method"

    private const val KEY_SCREEN_STATE = "mScreenState"

    fun inputMethodProperties(adbDevice: AdbDevice): Observable<Entry<String, String>> {

        return Adb.getInputMethodDumpsys(adbDevice)
                .flatMapIterable({ it.entries })
                .filter { " " in it.key }
                .sorted(Comparator.comparing<Entry<String, String>, String> { it.key })
    }

    fun displayProperties(adbDevice: AdbDevice): Observable<Entry<String, String>> {

        return Adb.getDisplayDumpsys(adbDevice)
                .flatMapIterable { it.entries }
                .filter { " " in it.key }
                .sorted(Comparator.comparing<Entry<String, String>, String> { it.key })
    }

    private fun AdbDevice?.isScreenOnSingle(): Single<Boolean> {

        return displayProperties(this!!)
                .filter { entry -> entry.key == KEY_SCREEN_STATE }
                .map { it -> PropHelper.hasPositiveValue(it) }
                .first(true) // TODO
    }

    fun isScreenOn(adbDevice: AdbDevice?): Boolean {
        return adbDevice.isScreenOnSingle().delay(2, TimeUnit.SECONDS).blockingGet()
    }
}