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
import junit.framework.AssertionFailedError
import java.util.concurrent.TimeUnit

class ExampleAndroidStepDefs {

    private val adbDevice: AdbDevice by lazy {

        val allDevices = AdbDeviceManager.getAllDevices()

        if (allDevices.isEmpty()) {
            throw NoConnectedAdbDeviceException()
        }

        val newConnectedDevice = allDevices.first().testHelper().adbDevice

        String.format("Connected device: %s", newConnectedDevice.toString())

        newConnectedDevice
    }

    private val testHelper: AdbDeviceTestHelper by lazy {
        adbDevice.testHelper()
    }

    @Before
    fun beforeScenario() {
        testHelper.startUiBus()
    }

    @After
    fun afterScenario() {
        testHelper.stopUiBus()
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
        testHelper.assertActivityName(activityName)
    }

    @Then("^I am not on the \"([^\"]*)\" activity$")
    @Throws(Throwable::class)
    fun assertNotActivityName(activityName: String) {
        testHelper.assertNotActivityName(activityName)
    }

    @Given("^I have a device with power at least (\\d+)$")
    @Throws(Throwable::class)
    fun connectDeviceAssertPower(minPower: Int) {
        testHelper.assertPower(minPower)
    }

    @Then("^I dismiss the dialog$")
    @Throws(Throwable::class)
    fun dismissDialog() {
        testHelper.dismissDialog()
    }

    @When("^I close the app \"([^\"]*)\"$")
    @Throws(Throwable::class)
    fun iCloseTheApp(packageName: String) {
        testHelper.forceStopApp(packageName)
    }

    @When("^I close the navigation drawer$")
    @Throws(Throwable::class)
    fun iCloseTheNavigationDrawer() {
        testHelper.dragLeft({ height -> height / 2 }, 0.2)
    }

    @When("^I dismiss the keyboard$")
    @Throws(Throwable::class)
    fun iDismissTheKeyboard() {
        testHelper.dismissKeyboard()
    }

    @Then("^I do not see the(?: text)? \"([^\"]*)\"(?: text)?$")
    @Throws(Throwable::class)
    fun iDoNotSeeTheText(text: String) {
        testHelper.failOnText(text, 2, TimeUnit.SECONDS)
    }

    @When("^I drag down$")
    @Throws(Throwable::class)
    fun dragDown() {
        testHelper.dragDown({ width -> width / 3 }, 0.2)
    }

    @When("^I drag down along the left side$")
    @Throws(Throwable::class)
    fun dragDownAlongTheLeftSide() {
        testHelper.dragDown({ width -> width / 3 }, 0.2)
    }

    @When("^I drag down along the right side$")
    @Throws(Throwable::class)
    fun dragDownAlongTheRightSide() {
        testHelper.dragDown({ width -> width - width / 3 }, 0.2)
    }

    @Then("^I drag down from the left side$")
    @Throws(Throwable::class)
    fun dragDownFromTheLeftSide() {
        testHelper.dragDown({ width -> width / 3 }, 0.2)
    }

    @Then("^I drag to scroll down$")
    @Throws(Throwable::class)
    fun dragToScrollDown() {
        testHelper.dragUp({ width -> width / 2 }, 0.2)
    }

    @Then("^I drag to scroll up$")
    @Throws(Throwable::class)
    fun dragToScrollUp() {
        testHelper.dragDown({ width -> width / 2 }, 0.2)
    }

    @When("^I drag up$")
    @Throws(Throwable::class)
    fun dragUp() {
        testHelper.dragUp({ width -> width / 2 }, 0.2)
    }

    @When("^I drag up along the left side$")
    @Throws(Throwable::class)
    fun dragUpAlongTheLeftSide() {
        testHelper.dragUp({ width -> width / 3 }, 0.2)
    }

    @When("^I drag up along the right side$")
    @Throws(Throwable::class)
    fun iDragUpAlongTheRightSide() {
        testHelper.dragUp({ width -> width - width / 3 }, 0.2)
    }

    @Then("^I drag up from the left side$")
    @Throws(Throwable::class)
    fun iDragUpFromTheLeftSide() {
        testHelper.dragUp({ width -> width / 3 }, 0.2)
    }

    @When("^I go back$")
    @Throws(Throwable::class)
    fun goBack() {
        testHelper.backButton()
    }

    @Given("^I have a connected device$")
    @Throws(Throwable::class)
    fun iHaveAConnectedDevice() {
        adbDevice

        // TODO auto wait for boot to complete
        // TODO auto unlock
    }

    @Then("^I have a connected device with the package \"([^\"]*)\" version \"([^\"]*)\"$")
    @Throws(Throwable::class)
    fun iHaveAConnectedDeviceWithThePackageVersion(packageName: String, versionName: String) {

        val packages = testHelper.getInstalledPackages()

        if (!packages.contains(packageName)) {
            throw AssertionFailedError("Packages list did not contain " + packageName)
        }

        if (!testHelper.installedPackageIsVersion(packageName, versionName)) {
            throw AssertionFailedError("Package was not correct version")
        }
    }

    @Then("^I have a connected device without the package \"([^\"]*)\"$")
    @Throws(Throwable::class)
    fun iHaveAConnectedDeviceWithoutThePackage(packageName: String) {

        val packages = testHelper.getInstalledPackages()

        if (packages.contains(packageName)) {
            throw AssertionFailedError("Packages list contained " + packageName)
        }
    }

    @When("^I open the navigation drawer$")
    @Throws(Throwable::class)
    fun iOpenTheNavigationDrawer() {

        // Drag from left to right from the halfway point of the screen
        testHelper.dragRight({ height -> height / 2 })
    }

    @When("^I press the home button$")
    @Throws(Throwable::class)
    fun iPressTheHomeButton() {
        adbDevice.pressKey().home()
    }

    @Then("^I see a button$")
    @Throws(Throwable::class)
    fun iSeeAButton() {
        testHelper.waitForUiNode { uiNode -> uiNode.isButton }
    }

    @Then("^I see the \"([^\"]*)\" text$")
    @Throws(Throwable::class)
    fun iSeeTheText(text: String) {
        testHelper.waitForText(text)
    }

    @Then("^I see the \"([^\"]*)\" button$")
    @Throws(Throwable::class)
    fun iSeeTheButton(text: String) {
        testHelper.waitForUiNode { it.isButton && text in it.text }
    }

    @When("^I see the text \"([^\"]*)\"$")
    @Throws(Throwable::class)
    fun iSeeTheText2(text: String) {
        testHelper.waitForText(text)
    }

    @Then("^I should see the lock screen$")
    @Throws(Throwable::class)
    fun iShouldSeeTheLockScreen() {
        testHelper.waitForUiNode { it.contains("com.android.systemui:id/keyguard_status_area") }
    }

    @When("^I swipe open the right drawer$")
    @Throws(Throwable::class)
    fun iSwipeOpenTheRightDrawer() {
        testHelper.dragLeft({ integer -> integer / 2 }, 0.2)
    }

    @When("^I type the \"([^\"]*)\" text$")
    @Throws(Throwable::class)
    fun iTypeTheText(text: String) {
        testHelper.typeText(text)
    }

    @When("^I swipe up$")
    @Throws(Throwable::class)
    fun swipeUp() {
        testHelper.dragUp({ width -> width / 2 }, 0.2)
    }

    @When("^I swipe up along the left side$")
    @Throws(Throwable::class)
    fun swipeUpAlongTheLeftSide() {
        testHelper.dragUp({ width -> width / 3 }, 0.2)
    }

    @When("^I swipe up along the right side$")
    @Throws(Throwable::class)
    fun swipeUpAlongTheRightSide() {
        testHelper.dragUp({ width -> width - width / 3 }, 0.2)
    }

    @Then("^I take a screenshot$")
    @Throws(Throwable::class)
    fun takeScreenshot() {
        testHelper.takeScreenshot()
    }

    @When("^I touch the \"([^\"]*)\" text$")
    @Throws(Throwable::class)
    fun iTouchTheText(text: String) {
        testHelper.touchText(text)
    }

    @When("^I touch the \"([^\"]*)\" button$")
    @Throws(Throwable::class)
    fun iTouchTheButton(text: String) {
        testHelper.touchUiNode { it.isButton && text in it.text }
    }

    @Then("^I wait$")
    @Throws(Throwable::class)
    fun iWait() {
        waitSeconds(3)
    }

    @Then("^I wait for the \"([^\"]*)\" text$")
    @Throws(Throwable::class)
    fun iWaitForTheText(text: String) {
        testHelper.waitForText(text)
    }

    @Then("^I wait for the \"([^\"]*)\" text to disappear$")
    @Throws(Throwable::class)
    fun iWaitForTheTextToDisappear(text: String) {
        testHelper.waitForTextToDisappear(text)
    }

    @Then("^I wait for \"([^\"]*)\" to appear$")
    @Throws(Throwable::class)
    fun iWaitForToAppear(text: String) {
        testHelper.waitForText(text)
    }

    @Then("^I wait up to (\\d+) seconds for \"([^\"]*)\" to appear$")
    @Throws(Throwable::class)
    fun iWaitUpToSecondsForToAppear(seconds: Int, text: String) {
        testHelper.waitForText(text)
    }

    @Then("^I wait up to (\\d+) seconds to see \"([^\"]*)\"$")
    @Throws(Throwable::class)
    fun iWaitUpToSecondsToSee(seconds: Int, text: String) {
        testHelper.waitForText(text)
    }

    @When("^I install the apk at \"([^\"]*)\"$")
    @Throws(Throwable::class)
    fun installApk(apkPath: String) {
        testHelper.installApk(apkPath)
    }

    @When("^I launch the app \"([^\"]*)\"$")
    @Throws(Throwable::class)
    fun launchApp(packageName: String) {
        testHelper.launchApp(packageName)
    }

    @When("^I launch the url \"([^\"]*)\"$")
    @Throws(Throwable::class)
    fun launchUrl(url: String) {
        testHelper.launchUrl(url)
    }

    @When("^I click the floating action button$")
    @Throws(Throwable::class)
    fun tapFloatingActionButton() {
        testHelper.touchUiNode { uiNode -> uiNode.contains("floatingaction") }
    }

    @When("^I turn the screen on$")
    @Throws(Throwable::class)
    fun turnScreenOn() {
        testHelper.turnScreenOn()
    }

    @When("^I uninstall the package \"([^\"]*)\"$")
    @Throws(Throwable::class)
    fun uninstallPackage(packageName: String) {
        testHelper.uninstallPackage(packageName)
    }

    @When("^I update the app with the apk at \"([^\"]*)\"$")
    @Throws(Throwable::class)
    fun updateApk(apkPath: String) {
        testHelper.updateApk(apkPath)
    }
}
