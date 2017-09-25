@file:Suppress("unused", "MemberVisibilityCanPrivate")

package co.herod.adbwrapper.model

import co.herod.adbwrapper.S
import co.herod.adbwrapper.getWindowBounds1
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class AdbDevice(
        var deviceIdentifier: String? = null,
        var type: String? = null
) : Disposable {

    val disposables: CompositeDisposable = CompositeDisposable()

    var preferredUiAutomatorStrategy = 0

    val windowBounds: UiBounds by lazy {
        @Suppress("DEPRECATION")
        getWindowBounds1()
    }

    val physical: Boolean by lazy {
        @Suppress("DEPRECATION")
        isConnectedDevice()
    }

    val emulator: Boolean by lazy {
        @Suppress("DEPRECATION")
        isEmulator()
    }

    override fun dispose() = disposables.dispose()
    override fun isDisposed() = disposables.isDisposed

    @Deprecated(replaceWith = ReplaceWith("physical"), message = "Use the 'physical' property")
    fun isConnectedDevice(): Boolean = type == S.DEVICE_CONNECTED_DEVICE

    @Deprecated(replaceWith = ReplaceWith("emulator"), message = "Use the 'emulator' property")
    fun isEmulator(): Boolean = type == S.DEVICE_EMULATOR

    override fun toString(): String {
        return "AdbDevice(deviceIdentifier=$deviceIdentifier, type=$type, preferredUiAutomatorStrategy=$preferredUiAutomatorStrategy)"
    }
}