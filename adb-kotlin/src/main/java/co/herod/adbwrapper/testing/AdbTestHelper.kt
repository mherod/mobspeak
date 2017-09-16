package co.herod.adbwrapper.testing

import co.herod.adbwrapper.*
import co.herod.adbwrapper.ext.pressButton
import co.herod.adbwrapper.ext.screen
import co.herod.adbwrapper.ext.turnOff
import co.herod.adbwrapper.ext.turnOn
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiNode
import co.herod.adbwrapper.rx.ResultChangeFixedDurationTransformer
import co.herod.adbwrapper.ext.containsIgnoreCase
import io.reactivex.Observable
import java.io.File
import java.lang.*
import java.util.concurrent.TimeUnit
import java.util.function.Predicate
import kotlin.AssertionError

/**
 * Created by matthewherod on 04/09/2017.
 */

object AdbTestHelper : AndroidTestHelper {

    var adbDevice: AdbDevice? = null

    override fun dismissKeyboard() = device {

        Adb.getInputMethodDumpsys(this)
                .map { it["mShowRequested"] }
                .blockingForEach {
                    if (it?.startsWith("true") == true) {
                        Adb.pressKeyBlocking(this, 111)

                        pressButton().escape()
                    }
                }
    }

    override fun assertScreenOn() {
        turnScreenOn()
    }

    override fun assertScreenOff() {
        turnScreenOff()
    }

    override fun turnScreenOff() = device {
        screen().turnOff()
    }

    override fun assertActivityName(activityName: String) = device {
        Adb.getWindowFocusDumpsys(this)
                .flatMapIterable { it.values }
                .filter { activityName.toLowerCase() in it.toLowerCase() }
                .firstOrError()
                .timeout(5, TimeUnit.SECONDS)
                .retry()
                .timeout(10, TimeUnit.SECONDS)
                .blockingGet().forEach { }
    }

    override fun assertNotActivityName(activityName: String) = device {
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

    override fun backButton() = device {
        pressButton().back()
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

    override fun dragDown(widthFunction: ((Int) -> Int), edgeOffset: Double) = device {

        val windowBounds = Adb.getWindowDumpsys(this)
                .map { it["mBounds"] }
                .map { it.substring(it.lastIndexOf('[') + 1, it.lastIndexOf(']')) }
                .map { it.split(',') }
                .blockingFirst()
        val width = Integer.parseInt(windowBounds[0])
        val height = Integer.parseInt(windowBounds[1])

        val x = widthFunction(width);

        swipe(
                x,
                (height * edgeOffset).toInt(),
                x,
                (height * (1.0 - edgeOffset)).toInt()
        )
    }

    override fun dragUp(widthFunction: ((Int) -> Int), edgeOffset: Double) = device {

        val windowBounds = Adb.getWindowDumpsys(this)
                .map { it["mBounds"] }
                .map { it.substring(it.lastIndexOf('[') + 1, it.lastIndexOf(']')) }
                .map { it.split(',') }
                .blockingFirst()
        val width = Integer.parseInt(windowBounds[0])
        val height = Integer.parseInt(windowBounds[1])

        val x = widthFunction(width);

        swipe(
                x,
                (height * (1.0 - edgeOffset)).toInt(),
                x,
                (height * edgeOffset).toInt()
        )
    }

    override fun dragRight(heightFunction: ((Int) -> Int), edgeOffset: Double) = device {

        val windowBounds = Adb.getWindowDumpsys(this)
                .map { it["mBounds"] }
                .map { it.substring(it.lastIndexOf('[') + 1, it.lastIndexOf(']')) }
                .map { it.split(',') }
                .blockingFirst()
        val width = Integer.parseInt(windowBounds[0])
        val height = Integer.parseInt(windowBounds[1])

        val y = heightFunction(height);

        swipe(
                (width * edgeOffset).toInt(),
                y,
                (width * (1.0 - edgeOffset)).toInt(),
                y
        )
    }

    override fun dragLeft(heightFunction: ((Int) -> Int), edgeOffset: Double) = device {

        val windowBounds = Adb.getWindowDumpsys(this)
                .map { it["mBounds"] }
                .map { it.substring(it.lastIndexOf('[') + 1, it.lastIndexOf(']')) }
                .map { it.split(',') }
                .blockingFirst()
        val width = Integer.parseInt(windowBounds[0])
        val height = Integer.parseInt(windowBounds[1])

        val y = heightFunction(height);

        swipe(
                (width * (1.0 - edgeOffset)).toInt(),
                y,
                (width * edgeOffset).toInt(),
                y
        )
    }

    override fun failOnText(text: String) = device {
        failOnText(text)
    }

    override fun failOnText(text: String, timeout: Int, timeUnit: TimeUnit) = device {
        failOnText(text, timeout, timeUnit)
    }

    override fun getInstalledPackages(): MutableList<String> = device {
        AdbPackageManager.listPackages(this)
    }

    override fun getPackageVersionName(packageName: String): String? = device {
        AdbPackageManager.getPackageVersionName(this, packageName)
    }

    override fun installApk(apkPath: String) = device {

        File(apkPath).assertExists()

        installPackage(apkPath)
    }

    override fun installedPackageIsVersion(packageName: String, versionName: String): Boolean =
            getPackageVersionName(packageName) == versionName

    override fun launchApp(packageName: String) = device {
        AdbPackageManager.launchApp(this, packageName)
    }

    override fun launchUrl(url: String) = device {
        AdbProcesses.launchUrl(this, url).blockingSubscribe()
    }

    override fun launchUrl(url: String, packageName: String) = device {
        AdbProcesses.launchUrl(this, url, packageName).blockingSubscribe()}

    override fun takeScreenshot(): Unit = device {
        screenshot(false)
    }

    override fun typeText(text: String) = device {
        typeText(text)
    }

    override fun touchUiNode(uiNodePredicate: Predicate<UiNode>) = device {
        waitForUiNodeForFunc(
                uiNodePredicate = Predicate {
                    uiNodePredicate.test(it)
                },
                function = {
                    tap(it)
                },
                timeout = 20,
                timeUnit = TimeUnit.SECONDS
        )
    }

    override fun touchText(text: String) = device {
        waitForUiNodeForFunc(
                uiNodePredicate = Predicate {
                    it.text.containsIgnoreCase(text)
                },
                function = {
                    tap(it)
                },
                timeout = 20,
                timeUnit = TimeUnit.SECONDS
        )
    }

    override fun turnScreenOn() = device {
        screen().turnOn()
    }

    override fun uninstallPackage(packageName: String) = device {
        uninstallPackage(packageName)
    }

    override fun updateApk(apkPath: String) = device {

        File(apkPath).assertExists()

        updatePackage(apkPath)
    }

    override fun File.assertExists() {
        assert(exists()) { "File does not exist at ${toPath()}" }
    }

    override fun waitForTextToFailToDisappear(text: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun waitForText(text: String, timeout: Int, timeUnit: TimeUnit) = device {
        waitForUiNodeForFunc(
                uiNodePredicate = Predicate {
                    it.text.containsIgnoreCase(text)
                },
                function = { "Found" },
                timeout = timeout,
                timeUnit = timeUnit
        )
    }

    override fun forceStopApp(packageName: String) = device {
        AdbPackageManager.forceStop(this, packageName)
    }

    override fun waitForUiNode(uiNodePredicate: Predicate<UiNode>) = device {
        waitForUiNodeForFunc(
                uiNodePredicate = uiNodePredicate,
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

        Observable.timer(1, TimeUnit.SECONDS)
                .flatMap { Adb.dumpUiNodes(this) }
                .timeout(timeout.toLong(), timeUnit)
                .blockingForEach { uiNode: UiNode ->
                    if (text.toLowerCase() in uiNode.text.toLowerCase()) {
                        throw AssertionError("Text was visible")
                    }
                }
    }

    private fun waitForUiNodeForFunc(
            uiNodePredicate: Predicate<UiNode>?,
            function: (UiNode) -> String? = { "No Action" },
            timeout: Int = 30,
            timeUnit: TimeUnit = TimeUnit.SECONDS
    ) {
        device {
            Adb.dumpUiNodes(this)
                    .compose(ResultChangeFixedDurationTransformer())
                    .filter { uiNodePredicate?.test(it) == true }
                    .timeout(8, timeUnit)
                    // .doOnNext(System.out::println)
                    .map { function(it).orEmpty() }
                    .retry()
                    .timeout(timeout.toLong(), timeUnit)
                    .blockingFirst()
        }
    }

    override fun waitForTextToDisappear(text: String) {

        try {
            waitForText(text, 10, TimeUnit.SECONDS)
        } catch (assertionError: AssertionError) {
            // ignore error if not found
        }
        waitSeconds(5)
        failOnText(text)
    }

    inline fun <R> device(block: AdbDevice.() -> R): R =
            adbDevice?.block() ?: throw NoConnectedAdbDeviceException()
}

class NoConnectedAdbDeviceException : AssertionError("No connected device!")
