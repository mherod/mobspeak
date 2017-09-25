@file:JvmName("ExampleAndroidStepDefs")

package skeleton

import co.herod.adbwrapper.AdbDeviceManager
import co.herod.adbwrapper.device.pressKey
import co.herod.adbwrapper.exceptions.NoConnectedAdbDeviceException
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.testing.*
import cucumber.api.PendingException
import cucumber.api.java.After
import cucumber.api.java.Before
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import io.reactivex.annotations.CheckReturnValue
import junit.framework.AssertionFailedError
import java.util.concurrent.TimeUnit

class ExampleAndroidStepDefs {

    private var adbDevice: AdbDevice? = null

    @Before
    fun beforeScenario() {
        // device().testHelper().startUiBus()
    }

    @After
    fun afterScenario() {
        // device().testHelper().stopUiBus()
    }

    private fun device(): AdbDevice {
        return adbDevice ?: this.tryConnectDevice()
    }

    @CheckReturnValue
    private fun tryConnectDevice(): AdbDevice {

        val allDevices = AdbDeviceManager.getAllDevices()

        if (allDevices.isEmpty()) {
            throw NoConnectedAdbDeviceException()
        }

        val newConnectedDevice = allDevices.first().testHelper().adbDevice

        String.format("Connected device: %s", newConnectedDevice.toString())

        return newConnectedDevice
    }

    @When("^a monkey snatches my device$")
    @Throws(Throwable::class)
    fun aMonkeySnatchesMyDevice() {
        // Write code here that turns the phrase above into concrete actions
        throw PendingException()
    }

    @Given("^I am on the \"([^\"]*)\" activity$")
    @Throws(Throwable::class)
    fun assertActivityName(activityName: String) {
        device().testHelper().assertActivityName(activityName)
    }

    @Then("^I am not on the \"([^\"]*)\" activity$")
    @Throws(Throwable::class)
    fun assertNotActivityName(activityName: String) {
        device().testHelper().assertNotActivityName(activityName)
    }

    @Given("^I have a device with power at least (\\d+)$")
    @Throws(Throwable::class)
    fun connectDeviceAssertPower(minPower: Int) {
        device().testHelper().assertPower(minPower)
    }

    @Then("^I dismiss the dialog$")
    @Throws(Throwable::class)
    fun dismissDialog() {
        device().testHelper().dismissDialog()
    }

    @When("^I am on the device app launcher$")
    @Throws(Throwable::class)
    fun iAmOnTheDeviceAppLauncher() {
        // Write code here that turns the phrase above into concrete actions
        throw PendingException()
    }

    @When("^I close the app \"([^\"]*)\"$")
    @Throws(Throwable::class)
    fun iCloseTheApp(packageName: String) {
        device().testHelper().forceStopApp(packageName)
    }

    @When("^I close the navigation drawer$")
    @Throws(Throwable::class)
    fun iCloseTheNavigationDrawer() {
        device().testHelper().closeLeftDrawer()
    }

    @When("^I dismiss the keyboard$")
    @Throws(Throwable::class)
    fun iDismissTheKeyboard() {
        device().testHelper().dismissKeyboard()
    }

    @Then("^I do not see any progress indicators$")
    @Throws(Throwable::class)
    fun iDoNotSeeAnyProgressIndicators() {
        // Write code here that turns the phrase above into concrete actions
        throw PendingException()
    }

    @Then("^I do not see the \"([^\"]*)\" text$")
    @Throws(Throwable::class)
    fun iDoNotSeeTheText(text: String) {
        device().testHelper().failOnText(text, 5, TimeUnit.SECONDS)
    }

    @Then("^I do not see the text \"([^\"]*)\"$")
    @Throws(Throwable::class)
    fun iDoNotSeeTheText2(text: String) {
        device().testHelper().failOnText(text, 5, TimeUnit.SECONDS)
    }

    @Then("^I do not see the text \"([^\"]*)\" disappear$")
    @Throws(Throwable::class)
    fun iDoNotSeeTheTextDisappear(arg0: String) {
        // Write code here that turns the phrase above into concrete actions
        throw PendingException()
    }

    @When("^I drag down$")
    @Throws(Throwable::class)
    fun dragDown() {
        device().testHelper().dragDown({ width -> width / 3 }, 0.2)
    }

    @When("^I drag down along the left side$")
    @Throws(Throwable::class)
    fun dragDownAlongTheLeftSide() {
        device().testHelper().dragDown({ width -> width / 3 }, 0.2)
    }

    @When("^I drag down along the right side$")
    @Throws(Throwable::class)
    fun dragDownAlongTheRightSide() {
        device().testHelper().dragDown({ width -> width - width / 3 }, 0.2)
    }

    @Then("^I drag down from the left side$")
    @Throws(Throwable::class)
    fun dragDownFromTheLeftSide() {
        device().testHelper().dragDown({ width -> width / 3 }, 0.2)
    }

    @Then("^I drag to scroll down$")
    @Throws(Throwable::class)
    fun dragToScrollDown() {
        device().testHelper().dragUp({ width -> width / 2 }, 0.2)
    }

    @Then("^I drag to scroll up$")
    @Throws(Throwable::class)
    fun dragToScrollUp() {
        device().testHelper().dragDown({ width -> width / 2 }, 0.2)
    }

    @When("^I drag up$")
    @Throws(Throwable::class)
    fun dragUp() {
        device().testHelper().dragUp({ width -> width / 2 }, 0.2)
    }

    @When("^I drag up along the left side$")
    @Throws(Throwable::class)
    fun dragUpAlongTheLeftSide() {
        device().testHelper().dragUp({ width -> width / 3 }, 0.2)
    }

    @When("^I drag up along the right side$")
    @Throws(Throwable::class)
    fun iDragUpAlongTheRightSide() {
        device().testHelper().dragUp({ width -> width - width / 3 }, 0.2)
    }

    @Then("^I drag up from the left side$")
    @Throws(Throwable::class)
    fun iDragUpFromTheLeftSide() {
        device().testHelper().dragUp({ width -> width / 3 }, 0.2)
    }

    @Then("^I enter \"([^\"]*)\" into \"([^\"]*)\"$")
    @Throws(Throwable::class)
    fun iEnterInto(arg0: String, arg1: String) {
        // Write code here that turns the phrase above into concrete actions
        throw PendingException()
    }

    @When("^I go back$")
    @Throws(Throwable::class)
    fun goBack() {
        device().testHelper().backButton()
    }

    @Given("^I have a connected device$")
    @Throws(Throwable::class)
    fun iHaveAConnectedDevice() {
        device()
    }

    @Then("^I have a connected device with the package \"([^\"]*)\" version \"([^\"]*)\"$")
    @Throws(Throwable::class)
    fun iHaveAConnectedDeviceWithThePackageVersion(packageName: String, versionName: String) {

        val packages = device().testHelper().getInstalledPackages()

        if (!packages.contains(packageName)) {
            throw AssertionFailedError("Packages list did not contain " + packageName)
        }

        if (!device().testHelper().installedPackageIsVersion(packageName, versionName)) {
            throw AssertionFailedError("Package was not correct version")
        }
    }

    @Then("^I have a connected device without the package \"([^\"]*)\"$")
    @Throws(Throwable::class)
    fun iHaveAConnectedDeviceWithoutThePackage(packageName: String) {

        val packages = device().testHelper().getInstalledPackages()

        if (packages.contains(packageName)) {
            throw AssertionFailedError("Packages list contained " + packageName)
        }
    }

    @When("^I open the navigation drawer$")
    @Throws(Throwable::class)
    fun iOpenTheNavigationDrawer() {

        // Drag from left to right from the halfway point of the screen
        device().testHelper().dragRight({ height -> height / 2 })
    }

    @When("^I press the home button$")
    @Throws(Throwable::class)
    fun iPressTheHomeButton() {
        device().pressKey().home()
    }

    @Then("^I see a button$")
    @Throws(Throwable::class)
    fun iSeeAButton() {
        device().testHelper().waitForUiNode { uiNode -> uiNode.uiClass.endsWith("Button") }
    }

    @Then("^I see the \"([^\"]*)\" text$")
    @Throws(Throwable::class)
    fun iSeeTheText(text: String) {
        device().testHelper().waitForText(text)
    }

    @When("^I see the text \"([^\"]*)\"$")
    @Throws(Throwable::class)
    fun iSeeTheText2(text: String) {
        device().testHelper().waitForText(text)
    }

    @Then("^I should see the lock screen$")
    @Throws(Throwable::class)
    fun iShouldSeeTheLockScreen() {
        device().testHelper().waitForUiNode { uiNode -> uiNode.contains("com.android.systemui:id/keyguard_status_area") }
    }

    @When("^I swipe open the right drawer$")
    @Throws(Throwable::class)
    fun iSwipeOpenTheRightDrawer() {
        device().testHelper().dragLeft(
                { integer -> integer / 2 },
                0.2
        )
    }

    @When("^I type the \"([^\"]*)\" text$")
    @Throws(Throwable::class)
    fun iTypeTheText(text: String) {
        device().testHelper().typeText(text)
    }

    @When("^I swipe up$")
    @Throws(Throwable::class)
    fun swipeUp() {
        device().testHelper().dragUp({ width -> width / 2 }, 0.2)
    }

    @When("^I swipe up along the left side$")
    @Throws(Throwable::class)
    fun swipeUpAlongTheLeftSide() {
        device().testHelper().dragUp({ width -> width / 3 }, 0.2)
    }

    @When("^I swipe up along the right side$")
    @Throws(Throwable::class)
    fun swipeUpAlongTheRightSide() {
        device().testHelper().dragUp({ width -> width - width / 3 }, 0.2)
    }

    @Then("^I take a screenshot$")
    @Throws(Throwable::class)
    fun takeScreenshot() {
        device().testHelper().takeScreenshot()
    }

    @When("^I touch the \"([^\"]*)\" text$")
    @Throws(Throwable::class)
    fun iTouchTheText(text: String) {
        device().testHelper().touchText(text)
    }

    @Then("^I wait$")
    @Throws(Throwable::class)
    fun iWait() {
        waitSeconds(3)
    }

    @Then("^I wait for the \"([^\"]*)\" text$")
    @Throws(Throwable::class)
    fun iWaitForTheText(text: String) {
        device().testHelper().waitForText(text)
    }

    @Then("^I wait for the \"([^\"]*)\" text to disappear$")
    @Throws(Throwable::class)
    fun iWaitForTheTextToDisappear(text: String) {
        device().testHelper().waitForTextToDisappear(text)
    }

    @Then("^I wait for \"([^\"]*)\" to appear$")
    @Throws(Throwable::class)
    fun iWaitForToAppear(text: String) {
        device().testHelper().waitForText(text)
    }

    @Then("^I wait up to (\\d+) seconds for \"([^\"]*)\" to appear$")
    @Throws(Throwable::class)
    fun iWaitUpToSecondsForToAppear(seconds: Int, text: String) {
        device().testHelper().waitForText(text)
    }

    @Then("^I wait up to (\\d+) seconds to see \"([^\"]*)\"$")
    @Throws(Throwable::class)
    fun iWaitUpToSecondsToSee(seconds: Int, text: String) {
        device().testHelper().waitForText(text)
    }

    @When("^I install the apk at \"([^\"]*)\"$")
    @Throws(Throwable::class)
    fun installApk(apkPath: String) {
        device().testHelper().installApk(apkPath)
    }

    @When("^I launch the app \"([^\"]*)\"$")
    @Throws(Throwable::class)
    fun launchApp(packageName: String) {
        device().testHelper().launchApp(packageName)
    }

    @When("^I launch the url \"([^\"]*)\"$")
    @Throws(Throwable::class)
    fun launchUrl(url: String) {
        device().testHelper().launchUrl(url)
    }

    @When("^I click the floating action button$")
    @Throws(Throwable::class)
    fun tapFloatingActionButton() {
        device().testHelper().touchUiNode { uiNode -> uiNode.contains("floatingaction") }
    }

    @When("^I turn the screen on$")
    @Throws(Throwable::class)
    fun turnScreenOn() {
        device().testHelper().turnScreenOn()
    }

    @When("^I uninstall the package \"([^\"]*)\"$")
    @Throws(Throwable::class)
    fun uninstallPackage(packageName: String) {
        device().testHelper().uninstallPackage(packageName)
    }

    @When("^I update the app with the apk at \"([^\"]*)\"$")
    @Throws(Throwable::class)
    fun updateApk(apkPath: String) {
        device().testHelper().updateApk(apkPath)
    }
}
