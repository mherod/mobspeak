package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.util.PropHelper
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*
import kotlin.collections.Map.Entry

internal object AdbDeviceProperties {

    const val PROPS_DISPLAY = "display"
    const val PROPS_INPUT_METHOD = "input_method"

    private const val KEY_SCREEN_STATE = "mScreenState"

    fun inputMethodProperties(adbDevice: AdbDevice): Flowable<Entry<String, String>> {

        return Flowable.just(adbDevice)
                .flatMap { Adb.getInputMethodDumpsys(adbDevice) }
                .flatMapIterable({ it.entries })
                .filter { " " in it.key }
                .sorted(Comparator.comparing<Entry<String, String>, String> { it.key })
    }

    fun displayProperties(adbDevice: AdbDevice): Flowable<Entry<String, String>> {

        return Flowable.just(adbDevice)
                .flatMap { Adb.getDisplayDumpsys(it) }
                .flatMapIterable({ it.entries })
                .filter { " " in it.key }
                .sorted(Comparator.comparing<Entry<String, String>, String> { it.key })
    }

    private fun AdbDevice?.isScreenOnSingle(): Single<Boolean> {

        return Flowable.just(this)
                .flatMap { displayProperties(it) }
                .filter { entry -> entry.key == KEY_SCREEN_STATE }
                .map { it -> PropHelper.hasPositiveValue(it) }
                .firstOrError()
    }

    fun isScreenOn(adbDevice: AdbDevice?): Boolean {
        return adbDevice.isScreenOnSingle().blockingGet()
    }
}