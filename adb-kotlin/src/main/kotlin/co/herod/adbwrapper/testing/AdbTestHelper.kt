package co.herod.adbwrapper.testing

import co.herod.adbwrapper.*
import co.herod.adbwrapper.device.*
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiNode
import co.herod.kotlin.ext.assertExists
import co.herod.kotlin.ext.blocking
import co.herod.kotlin.ext.containsIgnoreCase
import co.herod.kotlin.ext.timeout
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
                        pressKey().escape()
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

        dumpsys().windows()
                .flatMapIterable { it.values }
                .filter { it.containsIgnoreCase(activityName) }
                .firstOrError()
                .blocking(10, TimeUnit.SECONDS)
    }

    override fun assertNotActivityName(activityName: String) = device {

        dumpsys().windows()
                .flatMapIterable { it.values }
                .filter { (it.containsIgnoreCase(activityName)).not() }
                .firstOrError()
                .blocking(10, TimeUnit.SECONDS)
    }

    override fun assertPower(minPower: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun backButton() = device {
        pressKey().back()
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

        val x = widthFunction(width)

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

        val x = widthFunction(width)

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

        val y = heightFunction(height)

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

        val y = heightFunction(height)

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
        pm().installPackage(apkPath)
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
        AdbProcesses.launchUrl(this, url, packageName).blockingSubscribe()
    }

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
        pm().uninstallPackage(packageName)
    }

    override fun updateApk(apkPath: String) = device {

        File(apkPath).assertExists()

        pm().updatePackage(apkPath)
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
        pm().forceStop(packageName)
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
    ) = device {
        subscribeUiNodesSource()
                .filter { uiNodePredicate?.test(it) == true }
                .timeout(timeout, timeUnit)
                .map { function(it).orEmpty() }
                .blocking(timeout, timeUnit)
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
