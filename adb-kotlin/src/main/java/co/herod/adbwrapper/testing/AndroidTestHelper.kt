package co.herod.adbwrapper.testing

import co.herod.adbwrapper.model.AdbUiNode
import java.util.concurrent.TimeUnit
import java.util.function.Predicate

/**
 * Created by matthewherod on 05/09/2017.
 */

@Suppress("RedundantVisibilityModifier")
public interface AndroidTestHelper {

    fun assertActivityName(activityName: String)

    fun assertNotActivityName(activityName: String)

    fun assertPower(minPower: Int)

    fun assertValidApk(apkPath: String?)

    fun backButton()

    fun closeLeftDrawer()

    fun connectDevice()

    fun dismissDialog()

    fun dismissKeyboard()

    fun dragDown(widthFunction: Function1<Int, Int>, edgeOffset: Double = 0.2)

    fun dragUp(widthFunction: Function1<Int, Int>, edgeOffset: Double = 0.2)

    fun dragLeft(heightFunction: Function1<Int, Int>, edgeOffset: Double = 0.2)

    fun dragRight(heightFunction: Function1<Int, Int>, edgeOffset: Double = 0.2)

    fun failOnText(text: String)

    fun failOnText(text: String, timeout: Int = 30, timeUnit: TimeUnit = TimeUnit.SECONDS)

    fun getInstalledPackages(): List<String>

    fun getPackageVersionName(packageName: String): String?

    fun installApk(apkPath: String)

    fun installedPackageIsVersion(packageName: String, versionName: String): Boolean

    fun forceStopApp(packageName: String)

    fun launchApp(packageName: String)

    fun launchUrl(url: String)

    fun launchUrl(url: String, packageName: String)

    fun takeScreenshot()

    fun touchText(text: String)

    fun typeText(text: String)

    fun assertScreenOn()

    fun assertScreenOff()

    fun turnScreenOn()

    fun turnScreenOff()

    fun uninstallPackage(packageName: String)

    fun updateApk(apkPath: String)

    fun waitForTextToDisappear(text: String)

    fun waitForTextToFailToDisappear(text: String)

    fun waitForText(text: String, timeout: Int = 30, timeUnit: TimeUnit = TimeUnit.SECONDS)

    fun waitForUiNode(adbUiNodePredicate: Predicate<AdbUiNode>)
    fun waitSeconds(waitSeconds: Int = 3)

    fun touchUiNode(adbUiNodePredicate: Predicate<AdbUiNode>)
}
