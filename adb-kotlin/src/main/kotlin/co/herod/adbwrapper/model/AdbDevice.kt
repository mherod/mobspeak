@file:Suppress("unused", "MemberVisibilityCanPrivate")

package co.herod.adbwrapper.model

import co.herod.adbwrapper.S.Companion.DEVICE_CONNECTED_DEVICE
import co.herod.adbwrapper.S.Companion.DEVICE_EMULATOR
import co.herod.adbwrapper.bus.UiHierarchyBus
import co.herod.adbwrapper.props.lookupWindowBounds
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

data class AdbDevice(
        var deviceIdentifier: String,
        var type: String
) : Disposable {

    val disposables: CompositeDisposable = CompositeDisposable()

    var preferredUiAutomatorStrategy = 0

    val s1 = "temp"

    var rpcPort = 9008

    var lockPath: String = "/sdcard/test.lock"

    val windowBounds: UiBounds by lazy {
        @Suppress("DEPRECATION")
        lookupWindowBounds()
    }

    val physical: Boolean by lazy {
        @Suppress("DEPRECATION")
        (type == DEVICE_CONNECTED_DEVICE)
    }

    val emulator: Boolean by lazy {
        @Suppress("DEPRECATION")
        (type == DEVICE_EMULATOR)
    }

    val myUiHierarchyBus: UiHierarchyBus by lazy {
        UiHierarchyBus(this)
    }

    override fun dispose() = disposables.dispose()
    override fun isDisposed() = disposables.isDisposed

    override fun toString(): String {
        return "AdbDevice(deviceIdentifier='$deviceIdentifier', type='$type', disposables=$disposables, preferredUiAutomatorStrategy=$preferredUiAutomatorStrategy)"
    }

}