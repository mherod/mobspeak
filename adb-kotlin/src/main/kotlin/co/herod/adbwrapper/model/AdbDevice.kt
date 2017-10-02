@file:Suppress("unused", "MemberVisibilityCanPrivate")

package co.herod.adbwrapper.model

import co.herod.adbwrapper.S.Companion.DEVICE_CONNECTED_DEVICE
import co.herod.adbwrapper.S.Companion.DEVICE_EMULATOR
import co.herod.adbwrapper.props.lookupWindowBounds
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

data class AdbDevice(
        var deviceIdentifier: String,
        var type: String
) : Disposable {

    val disposables: CompositeDisposable = CompositeDisposable()

    var preferredUiAutomatorStrategy = 0

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

    override fun dispose() = disposables.dispose()
    override fun isDisposed() = disposables.isDisposed

    @Deprecated(
            replaceWith = ReplaceWith("physical"),
            message = "Use the 'physical' property"
    )
    fun isConnectedDevice(): Boolean {
        return physical
    }

    @Deprecated(
            replaceWith = ReplaceWith("emulator"),
            message = "Use the 'emulator' property"
    )
    fun isEmulator(): Boolean {
        return emulator
    }

    override fun toString(): String {
        return "AdbDevice(deviceIdentifier='$deviceIdentifier', type='$type', disposables=$disposables, preferredUiAutomatorStrategy=$preferredUiAutomatorStrategy)"
    }

}