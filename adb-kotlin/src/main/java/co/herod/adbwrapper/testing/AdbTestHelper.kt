package co.herod.adbwrapper.testing

import co.herod.adbwrapper.*
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

    private var _adbDevice: AdbDevice? = null
    var adbDevice: AdbDevice?
        get() = _adbDevice
        set(value) {
            _adbDevice = value
        }

    override fun assertScreenOn() {
        turnScreenOn()
    }

    override fun assertScreenOff() {
        turnScreenOff()
    }

    override fun turnScreenOff() {
        AdbDeviceActions.turnDeviceScreenOff(adbDevice)
    }

    override fun assertActivityName(activityName: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun assertPower(minPower: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun backButton() = AdbDeviceActions.pressBackButton(adbDevice)

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
        text?.let { adbDevice?.failOnText(it, 5, TimeUnit.SECONDS) }
    }

    override fun failOnText(text: String?, timeout: Int, timeUnit: TimeUnit?) {
        text?.let { adbDevice?.failOnText(it, timeout, TimeUnit.SECONDS) }
    }

    override fun getInstalledPackages(): MutableList<String>? {
        return AdbPackageManager.listPackages(adbDevice)?.blockingGet()
    }

    override fun getPackageVersionName(packageName: String?): String? {
        return packageName?.let { AdbPackageManager.getPackageVersionName(adbDevice, it) }
    }

    override fun installApk(apkPath: String?) {
        apkPath?.let { AdbPackageManager.installPackage(adbDevice, it) }
    }

    override fun installedPackageIsVersion(packageName: String?, versionName: String?): Boolean {
        return getPackageVersionName(packageName) == versionName
    }

    override fun launchApp(packageName: String?) {
        packageName?.let { AdbPackageManager.launchApp(adbDevice, it) }
    }

    override fun launchUrl(url: String?) {
        AdbProcesses.launchUrl(adbDevice, url)
    }

    override fun takeScreenshot() {
        adbDevice?.let { ScreenshotHelper.screenshot(it, false) };
    }

    override fun touchText(text: String?) {

        text?.let {
            Adb.dumpUiNodes(adbDevice)
                    .compose(ResultChangeFixedDurationTransformer())
                    .timeout(5, TimeUnit.SECONDS)
                    .onErrorResumeNext(Observable.empty<AdbUiNode>())
                    .blockingForEach { adbUiNode ->
                        when (it) {
                            in adbUiNode.text.toLowerCase() -> {
                                adbDevice?.let { adbDevice1 ->
                                    AdbDeviceActions.tapCentre(
                                            adbDevice1,
                                            adbUiNode
                                    )
                                }
                            }
                        }
                    }
        }
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
        adbDevice?.let {
            text?.let { text ->
                it.waitForText(
                        text = text,
                        timeout = 5,
                        timeUnit = TimeUnit.SECONDS
                )
            }
        }
    }

    override fun waitForText(text: String?, timeout: Int, timeUnit: TimeUnit?) {
        adbDevice?.let { waitForText(text, timeout, timeUnit) }
    }

    override fun waitForUiNode(adbUiNodePredicate: Predicate<AdbUiNode>?) {

        Adb.dumpUiNodes(adbDevice)
                .compose(ResultChangeFixedDurationTransformer())
                .filter {adbUiNode -> adbUiNodePredicate?.test(adbUiNode) == true }
                .timeout(10, TimeUnit.SECONDS)
                .subscribe()
    }

    override fun waitSeconds(waitSeconds: Int) = try {
        Thread.sleep((waitSeconds * 1000).toLong())
    } catch (ignored: InterruptedException) {
    }

    fun AdbDevice.failOnText(text: String, timeout: Int, timeUnit: TimeUnit) {

        Adb.dumpUiNodes(this)
                .compose(ResultChangeFixedDurationTransformer())
                .timeout(timeout.toLong(), timeUnit)
                .onErrorResumeNext(Observable.empty<AdbUiNode>())
                .blockingForEach { adbUiNode: AdbUiNode ->
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
