@file:JvmName("AdbTestHelperKt")
@file:Suppress("unused")

package co.herod.adbwrapper.testing

import co.herod.adbwrapper.*
import co.herod.adbwrapper.device.*
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.DumpsysKey
import co.herod.adbwrapper.model.UiNode
import co.herod.adbwrapper.model.isPropertyPositive
import co.herod.kotlin.ext.*
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class AdbDeviceTestHelper(val adbDevice: AdbDevice)

fun AdbDevice.testHelper() = AdbDeviceTestHelper(this)

fun AdbDeviceTestHelper.startUiBus(): Boolean = with(adbDevice) {
    disposables.add(streamUiHierarchy().subscribe())
}

fun AdbDeviceTestHelper.stopUiBus() = with(adbDevice) {
    dispose()
}

fun AdbDeviceTestHelper.dismissKeyboard() = with(adbDevice) {
    if (dumpsys().dump(dumpsysKey = DumpsysKey.INPUT_METHOD).isPropertyPositive("mShowRequested")) {
        pressKey().escape()
    }
}

fun AdbDeviceTestHelper.assertScreenOn() = with(adbDevice) {
    if (screen().isOn().not()) {
        throw AssertionError("Screen was not on")
    }
}

fun AdbDeviceTestHelper.assertScreenOff() = with(adbDevice) {
    if (screen().isOn()) {
        throw AssertionError("Screen was on")
    }
}

fun AdbDeviceTestHelper.turnScreenOff() = with(adbDevice) {
    screen().turnOff()
}

fun AdbDeviceTestHelper.turnScreenOn() = with(adbDevice) {
    screen().turnOn()
}

fun AdbDeviceTestHelper.assertActivityName(activityName: String) = with(adbDevice) {

    try {
        assert(matchActivity(activityName)) {
            "Did not see $activityName activity"
        }
    } catch (exception: IllegalStateException) {
        throw AssertionError(exception)
    }
}

fun AdbDeviceTestHelper.assertNotActivityName(activityName: String) = with(adbDevice) {

    try {
        assert(matchActivity(activityName).not()) {
            "Did not see $activityName activity"
        }
    } catch (exception: IllegalStateException) {
        throw AssertionError(exception)
    }
}

fun AdbDeviceTestHelper.assertWithoutPackage(packageName: String) = with(adbDevice) {

    val packages = pm().installedPackages()

    if (packages.contains(packageName)) {
        throw AssertionError("Packages list contained " + packageName)
    }
}

fun AdbDeviceTestHelper.assertInstalledPackageVersionName(packageName: String, versionName: String) = with(adbDevice) {

    val packages = pm().installedPackages()

    if (!packages.contains(packageName)) {
        throw AssertionError("Packages list did not contain " + packageName)
    }

    if (!installedPackageIsVersion(packageName, versionName)) {
        throw AssertionError("Package was not correct version")
    }
}

private fun AdbDevice.matchActivity(activityName: String): Boolean =
        Observable.just(this)
                .flatMap { device ->
                    device.dumpsys()
                            .dump(dumpsysKey = DumpsysKey.WINDOW)
                            .filterKeys("mCurrentFocus", "mFocusedApp")
                            .observableValues()
                            .filter { it.containsIgnoreCase(activityName) }
                }
                .firstOrError()
                .retryWithTimeout(10, TimeUnit.SECONDS)
                .onErrorReturn { "" } // timeout without match
                .doOnSuccess {
                    if (it.isNotBlank()) {
                        System.out.println("Matched: $it")
                    }
                }
                .blockingGet()
                .isNotBlank()

fun AdbDeviceTestHelper.assertPower(minPower: Int) {
    with(adbDevice) {
        TODO("not implemented: assertion for power $minPower")
    }
}

fun AdbDeviceTestHelper.backButton() = with(adbDevice) {
    pressKey().back()
}

fun AdbDeviceTestHelper.closeLeftDrawer() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

fun AdbDeviceTestHelper.dismissDialog() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

@JvmOverloads
fun AdbDeviceTestHelper.dragDown(
        widthFunction: ((Int) -> Int),
        edgeOffset: Double = 0.0
) = with(adbDevice) {
    windowBounds.run {
        widthFunction(width).let {
            swipe(it,
                    (height * edgeOffset).toInt(),
                    it,
                    (height * (1.0 - edgeOffset)).toInt())
        }
    }
}

@JvmOverloads
fun AdbDeviceTestHelper.dragUp(
        widthFunction: ((Int) -> Int),
        edgeOffset: Double = 0.0
) = with(adbDevice) {
    windowBounds.run {
        widthFunction(width).let {
            swipe(
                    it,
                    (height * (1.0 - edgeOffset)).toInt(),
                    it,
                    (height * edgeOffset).toInt()
            )
        }
    }
}

@JvmOverloads
fun AdbDeviceTestHelper.dragRight(
        heightFunction: ((Int) -> Int),
        edgeOffset: Double = 0.0
) = with(adbDevice) {
    windowBounds.run {
        heightFunction(height).let {
            swipe((width * edgeOffset).toInt(),
                    it,
                    (width * (1.0 - edgeOffset)).toInt(),
                    it)
        }
    }
}

@JvmOverloads
fun AdbDeviceTestHelper.dragLeft(
        heightFunction: ((Int) -> Int),
        edgeOffset: Double = 0.0
) = with(adbDevice) {
    windowBounds.run {
        heightFunction(height).let {
            swipe((width * (1.0 - edgeOffset)).toInt(),
                    it,
                    (width * edgeOffset).toInt(),
                    it)
        }
    }
}

fun AdbDeviceTestHelper.launchApp(packageName: String) = with(adbDevice) {
    AdbPackageManager.launchApp(this, packageName)
}

fun AdbDeviceTestHelper.launchUrl(url: String) = with(adbDevice) {
    launchUrl(this, url).blockingSubscribe()
}

fun AdbDeviceTestHelper.launchUrl(url: String, packageName: String) = with(adbDevice) {
    launchUrl(this, url, packageName).blockingSubscribe()
}

fun AdbDeviceTestHelper.takeScreenshot() = with(adbDevice) {
    screenshot(false)
}

fun AdbDeviceTestHelper.typeText(text: String) = with(adbDevice) {
    typeText(text)
}

fun AdbDeviceTestHelper.scrollUpUntilISee(function: (UiNode) -> Boolean) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

fun AdbDeviceTestHelper.touchUiNode(predicate: (UiNode) -> Boolean) =
        with(adbDevice) {
            waitForUiNodeForFunc(
                    predicate = predicate,
                    function = { tap(it) },
                    timeout = 10,
                    timeUnit = TimeUnit.SECONDS
            )
        }

fun AdbDeviceTestHelper.touchText(text: String) =
        with(adbDevice) {
            waitForUiNodeForFunc(
                    predicate = { it.text.containsIgnoreCase(text) },
                    function = { tap(it) },
                    timeout = 10,
                    timeUnit = TimeUnit.SECONDS
            )
        }

fun AdbDeviceTestHelper.uninstallPackage(packageName: String) = with(adbDevice) {

    pressKey().home() // go home first because input method can cause crash

    pm().uninstallPackage(packageName)
}


fun AdbDeviceTestHelper.waitForTextToFailToDisappear(text: String) {
    TODO("not implemented: waitForTextToDisappear $text")
}

@JvmOverloads
fun AdbDeviceTestHelper.waitForText(
        text: String,
        timeout: Int = 30,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): String =
        with(adbDevice) {
            waitForUiNodeForFunc(
                    { uiNode -> uiNode.text.containsIgnoreCase(text) },
                    timeout = timeout,
                    timeUnit = timeUnit
            )
        }

fun AdbDeviceTestHelper.waitForUiNode(
        predicate: (UiNode) -> Boolean
): String =
        waitForUiNodeForFunc(
                predicate = predicate,
                timeout = 10,
                timeUnit = TimeUnit.SECONDS
        )

fun AdbDeviceTestHelper.forceStopApp(packageName: String) = with(adbDevice) {
    pm().forceStop(packageName)
}


@JvmOverloads
fun waitSeconds(waitSeconds: Int = 3) = try {
    Thread.sleep((waitSeconds * 1000).toLong())
} catch (ignored: InterruptedException) {
}

@JvmOverloads
fun AdbDeviceTestHelper.failOnText(
        text: String,
        timeout: Int = 5,
        timeUnit: TimeUnit = TimeUnit.SECONDS
) = with(adbDevice) {
    Observable.timer(1, TimeUnit.SECONDS)
            .flatMap { Adb.dumpUiNodes(this, 30, TimeUnit.SECONDS) }
            .timeout(timeout.toLong(), timeUnit)
            .blockingForEach { uiNode: UiNode ->
                if (uiNode.text.containsIgnoreCase(text)) {
                    throw AssertionError("Text was visible")
                }
            }
}

private fun AdbDeviceTestHelper.waitForUiNodeForFunc(
        predicate: (UiNode) -> Boolean?,
        function: (UiNode) -> String? = { "" },
        timeout: Int = 30,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): String = with(adbDevice) {
    subscribeUiNodesSource()
            .filter { predicate(it) == true && it.visible } // filter for items passing predicate
            .firstOrError() // if not found in stream it will error
            .retry() // retry on error (stream finish before we match)
            .doOnSuccess {
                println("Matched: ${it.resourceId}")
            }
            .map { function(it).orEmpty() } // do this function with item
            .blocking(timeout, timeUnit) // with max max timeout
}

fun AdbDeviceTestHelper.waitForTextToDisappear(text: String) {

    try {
        waitForText(text, 10, TimeUnit.SECONDS)
    } catch (assertionError: AssertionError) {
        // ignore error if not found
    }
    waitSeconds(5)
    failOnText(text)
}