package co.herod.adbwrapper.testing

import co.herod.adbwrapper.Adb
import co.herod.adbwrapper.AdbDeviceActions
import co.herod.adbwrapper.AdbPackageManager
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.AdbUiNode
import co.herod.adbwrapper.rx.ResultChangeFixedDurationTransformer
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import java.util.function.Predicate

/**
 * Created by matthewherod on 04/09/2017.
 */

object AdbTestHelper : AndroidTestHelper {
    override fun assertScreenOn() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun assertScreenOff() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun turnScreenOff() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var adbDevice: AdbDevice? = null;

    override fun assertActivityName(activityName: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun assertPower(minPower: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun backButton() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun closeLeftDrawer() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun connectDevice() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dismissDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dragDown(widthFunction: ((Int) -> Int)?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dragUp(widthFunction: ((Int) -> Int)?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun failOnText(text: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun failOnText(text: String?, timeout: Int, timeUnit: TimeUnit?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getInstalledPackages(): MutableList<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPackageVersionName(packageName: String?): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun installApk(apkPath: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun installedPackageIsVersion(packageName: String?, versionName: String?): Boolean {
        return getPackageVersionName(packageName) == versionName;
    }

    override fun launchApp(packageName: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun launchUrl(url: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun takeScreenshot() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun touchText(text: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun turnScreenOn() {
        AdbDeviceActions.turnDeviceScreenOn(adbDevice)
    }

    override fun uninstallPackage(packageName: String?) {
        packageName?.let { AdbPackageManager.uninstallPackage(adbDevice, it) }
    }

    override fun updateApk(apkPath: String?) {
        apkPath?.let { AdbPackageManager.updatePackage(adbDevice, it) }
    }

    override fun waitForText(text: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun waitForText(text: String?, timeout: Int, timeUnit: TimeUnit?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun waitForUiNode(adbUiNodePredicate: Predicate<AdbUiNode>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun waitSeconds(waitSeconds: Int) = try {
        Thread.sleep((waitSeconds * 1000).toLong())
    } catch (ignored: InterruptedException) {
    }

    fun AdbDevice.failOnText(text: String, timeout: Long, timeUnit: TimeUnit) {

        Adb.dumpUiNodes(this)
                .compose(ResultChangeFixedDurationTransformer())
                .timeout(timeout, timeUnit)
                .onErrorResumeNext(Observable.empty<AdbUiNode>())
                .blockingForEach { adbUiNode ->
                    if (text.toLowerCase() in adbUiNode.text.toLowerCase()) {
                        throw AssertionFailedError("Text was visible")
                    }
                }
    }

    fun AdbDevice.waitForText(text: String, timeout: Long, timeUnit: TimeUnit) {

        try {
            Adb.dumpUiNodes(this)
                    .filter { text in it.text }
                    .timeout(timeout, timeUnit)
                    .blockingForEach { throw BlockingBreakerThrowable() }
        } catch (e: BlockingBreakerThrowable) {
            // good!
        }
    }
}

class BlockingBreakerThrowable : RuntimeException()
