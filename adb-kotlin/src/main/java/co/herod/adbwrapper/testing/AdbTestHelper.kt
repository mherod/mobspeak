package co.herod.adbwrapper.testing

import co.herod.adbwrapper.*
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.AdbUiNode
import co.herod.adbwrapper.rx.ResultChangeFixedDurationTransformer
import io.reactivex.Observable
import java.io.File
import java.lang.AssertionError
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
        withAdbDevice {
            AdbDeviceActions.turnDeviceScreenOff(this)
        }
    }

    override fun assertActivityName(activityName: String?) {
        withAdbDevice {
            Adb.getWindowFocusDumpsys(this)
                    .flatMapIterable { it.values }
                    .filter { activityName?.toLowerCase().toString() in it.toLowerCase() }
                    .firstOrError()
                    .timeout(5, TimeUnit.SECONDS)
                    .retry()
                    .timeout(10, TimeUnit.SECONDS)
        }
    }

    override fun assertPower(minPower: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun backButton() = withAdbDevice { AdbDeviceActions.pressBackButton(this) }

    override fun closeLeftDrawer() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun connectDevice() {
        adbDevice = AdbDeviceManager.getConnectedDevice()
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
        withAdbDevice {
            return packageName?.let {
                AdbPackageManager.getPackageVersionName(adbDevice, it)
            }
        }
    }

    override fun installApk(apkPath: String?) {

        assertValidApk(apkPath)

        apkPath?.let {
            AdbPackageManager.installPackage(adbDevice, it)
        }
    }

    override fun installedPackageIsVersion(packageName: String?, versionName: String?): Boolean {
        return getPackageVersionName(packageName) == versionName
    }

    override fun launchApp(packageName: String?) {

        withAdbDevice {
            packageName?.let { packageName ->
                AdbPackageManager.launchApp(
                        this,
                        packageName
                )
            } ?: throw AssertionError("Package name is null")
        }
    }

    override fun launchUrl(url: String?) {
        withAdbDevice {
            AdbProcesses.launchUrlObservable(this, url).blockingSubscribe()
        }
    }

    override fun takeScreenshot() {
        withAdbDevice {
            ScreenshotHelper.screenshot(this, false)
        }
    }

    override fun touchText(text: String?) {

        val lowerCaseText: String? = text?.toLowerCase()

        withAdbDevice {
            lowerCaseText?.let { lowerCaseText ->
                waitForUiNodeForFunc(
                        adbUiNodePredicate = Predicate {
                            lowerCaseText in it.text.toLowerCase()
                        },
                        function = {
                            AdbDeviceActions.tapCentre(this, it)
                        },
                        timeout = 20,
                        timeUnit = TimeUnit.SECONDS
                )
            } ?: throw AssertionError("Cannot touch empty text")
        }
    }

    override fun turnScreenOn() {
        withAdbDevice {
            AdbDeviceActions.turnDeviceScreenOn(this)
        }
    }

    override fun uninstallPackage(packageName: String?) {
        withAdbDevice {
            packageName?.let {
                AdbPackageManager.uninstallPackage(this, it)
            }
        }
    }

    override fun updateApk(apkPath: String?) {

        assertValidApk(apkPath)

        withAdbDevice {
            apkPath?.let {
                AdbPackageManager.updatePackage(this, it)
            }
        }
    }

    override fun assertValidApk(apkPath: String?) {
        assert(!File(apkPath).exists().not()) { "Apk does not exist at $apkPath" }
    }

    override fun waitForText(text: String?) {
        waitForText(text, 10, TimeUnit.SECONDS)
    }

    override fun waitForText(text: String?, timeout: Int, timeUnit: TimeUnit) {

        val lowerCaseText: String? = text?.toLowerCase()

        withAdbDevice {
            lowerCaseText?.let { lowerCaseText ->
                waitForUiNodeForFunc(
                        adbUiNodePredicate = Predicate {
                            lowerCaseText in it.text.toLowerCase()
                        },
                        function = { "Found" },
                        timeout = timeout,
                        timeUnit = timeUnit
                )
            } ?: throw AssertionError("Cannot touch empty text")
        }
    }

    override fun waitForUiNode(adbUiNodePredicate: Predicate<AdbUiNode>?) {

        withAdbDevice {
            waitForUiNodeForFunc(
                    adbUiNodePredicate = adbUiNodePredicate,
                    function = { "Found" },
                    timeout = 20,
                    timeUnit = TimeUnit.SECONDS
            )
        }
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
                .doOnNext(System.out::println)
                .blockingForEach { adbUiNode: AdbUiNode ->
                    if (text.toLowerCase() in adbUiNode.text.toLowerCase()) {
                        throw AssertionError("Text was visible")
                    }
                }
    }

//    fun AdbDevice.waitForText(text: String, timeout: Int, timeUnit: TimeUnit) {
//
//        try {
//            Adb.dumpUiNodes(this)
//                    .filter { text in it.text }
//                    .timeout(timeout.toLong(), timeUnit)
//                    .doOnNext(System.out::println)
//                    .blockingForEach { throw BlockingBreakerThrowable() }
//        } catch (e: BlockingBreakerThrowable) {
//            // good!
//        }
//    }

    private fun waitForUiNodeForFunc(adbUiNodePredicate: Predicate<AdbUiNode>?, function: (AdbUiNode) -> String?, timeout: Int, timeUnit: TimeUnit) {

        withAdbDevice {
            Adb.dumpUiNodes(this)
                    .compose(ResultChangeFixedDurationTransformer())
                    .filter { adbUiNodePredicate?.test(it) == true }
                    .timeout(8, timeUnit)
                    .doOnNext(System.out::println)
                    .map { function(it).orEmpty() }
                    .retry()
                    .timeout(timeout.toLong(), timeUnit)
                    .blockingFirst()
        }
    }

    private inline fun <R> withAdbDevice(block: AdbDevice.() -> R): R {
        return adbDevice?.block() ?: throw NoConnectedAdbDeviceException()
    }
}

class NoConnectedAdbDeviceException : AssertionError("No connected device!")
class BlockingBreakerThrowable : RuntimeException()
