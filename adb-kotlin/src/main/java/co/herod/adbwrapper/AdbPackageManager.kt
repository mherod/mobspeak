package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import io.reactivex.Single
import java.util.*

/**
 * Created by matthewherod on 04/09/2017.
 */

object AdbPackageManager {

    private const val SPACE = " "
    private const val UNINSTALL = "uninstall"

    fun launchApp(adbDevice: AdbDevice?, packageName: String) {
        Adb.blocking(adbDevice, "shell monkey -p $packageName 1")
    }

    fun uninstallPackage(adbDevice: AdbDevice?, packageName: String) {

        //noinspection StringBufferReplaceableByString,StringBufferWithoutInitialCapacity
        Adb.blocking(adbDevice, StringBuilder()
                .append(UNINSTALL)
                .append(SPACE)
                .append(packageName)
                .toString())
    }

    fun installPackage(adbDevice: AdbDevice?, apkPath: String) {
        Adb.blocking(adbDevice, "install $apkPath")
    }

    fun updatePackage(adbDevice: AdbDevice?, apkPath: String) {
        Adb.blocking(adbDevice, "install -r $apkPath")
    }

    fun listPackages(adbDevice: AdbDevice?): Single<MutableList<String>>? {
        return Adb.command(adbDevice, "shell pm list packages")
                .filter { it.contains(":") }
                .map { it.split(":").last() }
                .toSortedList()
                .onErrorReturn { Collections.emptyList() }
    }
}
