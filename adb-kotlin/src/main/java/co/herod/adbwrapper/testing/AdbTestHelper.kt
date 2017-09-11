package co.herod.adbwrapper.testing

import co.herod.adbwrapper.*
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.AdbUiNode
import co.herod.adbwrapper.rx.ResultChangeFixedDurationTransformer
import java.io.File
import java.lang.*
import java.util.concurrent.TimeUnit
import java.util.function.Predicate
import kotlin.AssertionError

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

    override fun turnScreenOff() = withAdbDevice {
        AdbDeviceActions.turnDeviceScreenOff(this)
    }

    override fun assertActivityName(activityName: String) = withAdbDevice {
        Adb.getWindowFocusDumpsys(this)
                .flatMapIterable { it.values }
                .filter { activityName.toLowerCase() in it.toLowerCase() }
                .firstOrError()
                .timeout(5, TimeUnit.SECONDS)
                .retry()
                .timeout(10, TimeUnit.SECONDS)
                .blockingGet().forEach { }
    }

    override fun assertNotActivityName(activityName: String) = withAdbDevice {
        Adb.getWindowFocusDumpsys(this)
                .flatMapIterable { it.values }
                .doOnNext(System.out::println)
                .filter { (activityName.toLowerCase() in it.toLowerCase()).not() }
                .firstOrError()
                .timeout(5, TimeUnit.SECONDS)
                .retry()
                .timeout(10, TimeUnit.SECONDS)
                .blockingGet().forEach {  }
    }

    override fun assertPower(minPower: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun backButton() = withAdbDevice {
        AdbDeviceActions.pressBackButton(this)
    }

    override fun closeLeftDrawer() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun connectDevice() {
        adbDevice = AdbDeviceManager.getConnectedDevice()
    }

    override fun dismissDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dragDown(widthFunction: ((Int) -> Int), edgeOffset: Double) = withAdbDevice {

        val windowBounds = Adb.getWindowDumpsys(this)
                .map { it["mBounds"] }
                .map { it.substring(it.lastIndexOf('[') + 1, it.lastIndexOf(']')) }
                .map { it.split(',') }
                .blockingFirst()
        val width = Integer.parseInt(windowBounds[0])
        val height = Integer.parseInt(windowBounds[1])

        val x = widthFunction(width);

        AdbDeviceActions.swipe(
                this,
                x,
                (height * edgeOffset).toInt(),
                x,
                (height * (1.0 - edgeOffset)).toInt()
        )
    }

    override fun dragUp(widthFunction: ((Int) -> Int), edgeOffset: Double) = withAdbDevice {

        val windowBounds = Adb.getWindowDumpsys(this)
                .map { it["mBounds"] }
                .map { it.substring(it.lastIndexOf('[') + 1, it.lastIndexOf(']')) }
                .map { it.split(',') }
                .blockingFirst()
        val width = Integer.parseInt(windowBounds[0])
        val height = Integer.parseInt(windowBounds[1])

        val x = widthFunction(width);

        AdbDeviceActions.swipe(
                this,
                x,
                (height * (1.0 - edgeOffset)).toInt(),
                x,
                (height * edgeOffset).toInt()
        )
    }

    override fun dragRight(heightFunction: ((Int) -> Int), edgeOffset: Double) = withAdbDevice {

        val windowBounds = Adb.getWindowDumpsys(this)
                .map { it["mBounds"] }
                .map { it.substring(it.lastIndexOf('[') + 1, it.lastIndexOf(']')) }
                .map { it.split(',') }
                .blockingFirst()
        val width = Integer.parseInt(windowBounds[0])
        val height = Integer.parseInt(windowBounds[1])

        val y = heightFunction(height);

        AdbDeviceActions.swipe(
                this,
                (width * edgeOffset).toInt(),
                y,
                (width * (1.0 - edgeOffset)).toInt(),
                y
        )
    }

    override fun dragLeft(heightFunction: ((Int) -> Int), edgeOffset: Double) = withAdbDevice {

        val windowBounds = Adb.getWindowDumpsys(this)
                .map { it["mBounds"] }
                .map { it.substring(it.lastIndexOf('[') + 1, it.lastIndexOf(']')) }
                .map { it.split(',') }
                .blockingFirst()
        val width = Integer.parseInt(windowBounds[0])
        val height = Integer.parseInt(windowBounds[1])

        val y = heightFunction(height);

        AdbDeviceActions.swipe(
                this,
                (width * (1.0 - edgeOffset)).toInt(),
                y,
                (width * edgeOffset).toInt(),
                y
        )
    }

    override fun failOnText(text: String) = withAdbDevice {
        failOnText(text)
    }

    override fun failOnText(text: String, timeout: Int, timeUnit: TimeUnit) = withAdbDevice {
        failOnText(text, timeout, timeUnit)
    }

    override fun getInstalledPackages(): MutableList<String> = withAdbDevice {
        AdbPackageManager.listPackages(this)
    }

    override fun getPackageVersionName(packageName: String): String? = withAdbDevice {
        AdbPackageManager.getPackageVersionName(this, packageName)
    }

    override fun installApk(apkPath: String) = withAdbDevice {

        assertValidApk(apkPath)

        AdbPackageManager.installPackage(this, apkPath)
    }

    override fun installedPackageIsVersion(packageName: String, versionName: String): Boolean =
            getPackageVersionName(packageName) == versionName

    override fun launchApp(packageName: String) = withAdbDevice {
        AdbPackageManager.launchApp(this, packageName)
    }

    override fun launchUrl(url: String) = withAdbDevice {
        AdbProcesses.launchUrl(this, url).blockingSubscribe()
    }

    override fun launchUrl(url: String, packageName: String) = withAdbDevice {
        AdbProcesses.launchUrl(this, url, packageName).blockingSubscribe()}

    override fun takeScreenshot(): Unit = withAdbDevice {
        ScreenshotHelper.screenshot(this, false)
    }

    override fun touchUiNode(adbUiNodePredicate: Predicate<AdbUiNode>) = withAdbDevice {
        waitForUiNodeForFunc(
                adbUiNodePredicate = Predicate {
                    adbUiNodePredicate.test(it)
                },
                function = {
                    AdbDeviceActions.tapCentre(this, it)
                },
                timeout = 20,
                timeUnit = TimeUnit.SECONDS
        )
    }

    override fun touchText(text: String) = withAdbDevice {
        waitForUiNodeForFunc(
                adbUiNodePredicate = Predicate {
                    text.toLowerCase() in it.text.toLowerCase()
                },
                function = {
                    AdbDeviceActions.tapCentre(this, it)
                },
                timeout = 20,
                timeUnit = TimeUnit.SECONDS
        )
    }

    override fun turnScreenOn() = withAdbDevice {
        AdbDeviceActions.turnDeviceScreenOn(this)
    }

    override fun uninstallPackage(packageName: String) = withAdbDevice {
        AdbPackageManager.uninstallPackage(this, packageName)
    }

    override fun updateApk(apkPath: String) = withAdbDevice {

        assertValidApk(apkPath)

        AdbPackageManager.updatePackage(this, apkPath)
    }

    override fun assertValidApk(apkPath: String?) {
        assert(!File(apkPath).exists().not()) { "Apk does not exist at $apkPath" }
    }

    override fun waitForTextToFailToDisappear(text: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun waitForText(text: String, timeout: Int, timeUnit: TimeUnit) = withAdbDevice {
        waitForUiNodeForFunc(
                adbUiNodePredicate = Predicate {
                    text.toLowerCase() in it.text.toLowerCase()
                },
                function = { "Found" },
                timeout = timeout,
                timeUnit = timeUnit
        )
    }

    override fun forceStopApp(packageName: String) = withAdbDevice {
        AdbPackageManager.forceStop(this, packageName)
    }

    override fun waitForUiNode(adbUiNodePredicate: Predicate<AdbUiNode>) = withAdbDevice {
        waitForUiNodeForFunc(
                adbUiNodePredicate = adbUiNodePredicate,
                function = { "Found" },
                timeout = 20,
                timeUnit = TimeUnit.SECONDS
        )
    }

    override fun waitSeconds(waitSeconds: Int) = try {
        Thread.sleep((waitSeconds * 1000).toLong())
    } catch (ignored: InterruptedException) {
    }

    private fun AdbDevice.failOnText(text: String, timeout: Int = 5, timeUnit: TimeUnit = TimeUnit.SECONDS) {

        Adb.dumpUiNodes(this)
                .timeout(timeout.toLong(), timeUnit)
                .blockingForEach { adbUiNode: AdbUiNode ->
                    if (text.toLowerCase() in adbUiNode.text.toLowerCase()) {
                        throw AssertionError("Text was visible")
                    }
                }
    }

    private fun waitForUiNodeForFunc(
            adbUiNodePredicate: Predicate<AdbUiNode>?,
            function: (AdbUiNode) -> String?,
            timeout: Int,
            timeUnit: TimeUnit
    ) {
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

    override fun waitForTextToDisappear(text: String) {

        try {
            waitForText(text, 30, TimeUnit.SECONDS)
        } catch (assertionError: AssertionError) {
            // ignore error if not found
        }
        failOnText(text)
    }

    private inline fun <R> withAdbDevice(block: AdbDevice.() -> R): R {
        return adbDevice?.block() ?: throw NoConnectedAdbDeviceException()
    }
}

class NoConnectedAdbDeviceException : AssertionError("No connected device!")
