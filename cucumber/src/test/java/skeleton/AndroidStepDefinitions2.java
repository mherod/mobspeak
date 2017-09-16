package skeleton;

import co.herod.adbwrapper.testing.AdbTestHelper;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.AssertionFailedError;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AndroidStepDefinitions2 {

    public static final double DEFAULT_EDGE_OFFSET = 0.2;

    public AndroidStepDefinitions2() {

    }

    @When("^a monkey snatches my device$")
    public void aMonkeySnatchesMyDevice() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^I am on the \"([^\"]*)\" activity$")
    public void assertActivityName(String activityName) throws Throwable {

        AdbTestHelper.INSTANCE.assertActivityName(activityName);
    }

    @Then("^I am not on the \"([^\"]*)\" activity$")
    public void assertNotActivityName(String activityName) throws Throwable {

        AdbTestHelper.INSTANCE.assertNotActivityName(activityName);
    }

    @Given("^I have a device with power at least (\\d+)$")
    public void connectDeviceAssertPower(int minPower) throws Throwable {

        AdbTestHelper.INSTANCE.connectDevice();
        AdbTestHelper.INSTANCE.assertPower(minPower);
    }

    @Then("^I dismiss the dialog$")
    public void dismissDialog() throws Throwable {

        AdbTestHelper.INSTANCE.dismissDialog();
    }

    @When("^I am on the device app launcher$")
    public void iAmOnTheDeviceAppLauncher() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I close the app \"([^\"]*)\"$")
    public void iCloseTheApp(String packageName) throws Throwable {

        AdbTestHelper.INSTANCE.forceStopApp(packageName);
    }

    @When("^I close the navigation drawer$")
    public void iCloseTheNavigationDrawer() throws Throwable {
        AdbTestHelper.INSTANCE.closeLeftDrawer();
    }

    @When("^I dismiss the keyboard$")
    public void iDismissTheKeyboard() throws Throwable {
        AdbTestHelper.INSTANCE.dismissKeyboard();
    }

    @Then("^I do not see any progress indicators$")
    public void iDoNotSeeAnyProgressIndicators() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I do not see any whitespace$")
    public void iDoNotSeeAnyWhitespace() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I do not see the \"([^\"]*)\" text$")
    public void iDoNotSeeTheText(String text) throws Throwable {

        AdbTestHelper.INSTANCE.waitSeconds(2);
        AdbTestHelper.INSTANCE.failOnText(text, 5, TimeUnit.SECONDS);
    }

    @Then("^I do not see the text \"([^\"]*)\"$")
    public void iDoNotSeeTheText2(String text) throws Throwable {

        AdbTestHelper.INSTANCE.waitSeconds(2);
        AdbTestHelper.INSTANCE.failOnText(text, 5, TimeUnit.SECONDS);
    }

    @Then("^I do not see the text \"([^\"]*)\" disappear$")
    public void iDoNotSeeTheTextDisappear(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I drag down$")
    public void dragDown() throws Throwable {
        AdbTestHelper.INSTANCE.dragDown(width -> width / 3, DEFAULT_EDGE_OFFSET);
    }

    @When("^I drag down along the left side$")
    public void dragDownAlongTheLeftSide() throws Throwable {
        AdbTestHelper.INSTANCE.dragDown(width -> width / 3, DEFAULT_EDGE_OFFSET);
    }

    @When("^I drag down along the right side$")
    public void dragDownAlongTheRightSide() throws Throwable {
        AdbTestHelper.INSTANCE.dragDown(width -> width - (width / 3), DEFAULT_EDGE_OFFSET);
    }

    @Then("^I drag down from the left side$")
    public void dragDownFromTheLeftSide() throws Throwable {
        AdbTestHelper.INSTANCE.dragDown(width -> width / 3, DEFAULT_EDGE_OFFSET);
    }

    @Then("^I drag to scroll down$")
    public void dragToScrollDown() throws Throwable {

        AdbTestHelper.INSTANCE.dragUp(width -> width / 2, DEFAULT_EDGE_OFFSET);
    }

    @Then("^I drag to scroll up$")
    public void dragToScrollUp() throws Throwable {

        AdbTestHelper.INSTANCE.dragDown(width -> width / 2, DEFAULT_EDGE_OFFSET);
    }

    @When("^I drag up$")
    public void dragUp() throws Throwable {

        AdbTestHelper.INSTANCE.dragUp(width -> width / 2, DEFAULT_EDGE_OFFSET);
    }

    @When("^I drag up along the left side$")
    public void dragUpAlongTheLeftSide() throws Throwable {
        AdbTestHelper.INSTANCE.dragUp(width -> width / 3, DEFAULT_EDGE_OFFSET);
    }

    @When("^I drag up along the right side$")
    public void iDragUpAlongTheRightSide() throws Throwable {

        AdbTestHelper.INSTANCE.dragUp(width -> width - (width / 3), DEFAULT_EDGE_OFFSET);
    }

    @Then("^I drag up from the left side$")
    public void iDragUpFromTheLeftSide() throws Throwable {
        AdbTestHelper.INSTANCE.dragUp(width -> width / 3, DEFAULT_EDGE_OFFSET);
    }

    @Then("^I enter \"([^\"]*)\" into \"([^\"]*)\"$")
    public void iEnterInto(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I enter \"([^\"]*)\" into input field number (\\d+)$")
    public void iEnterIntoInputFieldNumber(String arg0, int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I enter text \"([^\"]*)\" into field with id \"([^\"]*)\"$")
    public void iEnterTextIntoFieldWithId(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I go back$")
    public void goBack() throws Throwable {

        AdbTestHelper.INSTANCE.backButton();
    }

    @Given("^I have a connected device$")
    public void iHaveAConnectedDevice() throws Throwable {

        AdbTestHelper.INSTANCE.connectDevice();
    }

    @Then("^I have a connected device with the package \"([^\"]*)\" version \"([^\"]*)\"$")
    public void iHaveAConnectedDeviceWithThePackageVersion(String packageName, String versionName) throws Throwable {

        final List<String> packages = AdbTestHelper.INSTANCE.getInstalledPackages();

        if (!packages.contains(packageName)) {
            throw new AssertionFailedError("Packages list did not contain " + packageName);
        }

        // TODO check version

        if (!AdbTestHelper.INSTANCE.installedPackageIsVersion(packageName, versionName)) {
            throw new AssertionFailedError("Package was not correct version");
        }
    }

    @Then("^I have a connected device without the package \"([^\"]*)\"$")
    public void iHaveAConnectedDeviceWithoutThePackage(String packageName) throws Throwable {

        final List<String> packages = AdbTestHelper.INSTANCE.getInstalledPackages();

        if (packages.contains(packageName)) {
            throw new AssertionFailedError("Packages list contained " + packageName);
        }
    }

    @When("^I login with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void iLoginWithUsernameAndPassword(String username, String password) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I navigate to \"([^\"]*)\"$")
    public void iNavigateTo(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I open the navigation drawer$")
    public void iOpenTheNavigationDrawer() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I press the home button$")
    public void iPressTheHomeButton() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I scroll until I see the text \"([^\"]*)\"$")
    public void iScrollUntilISeeTheText(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I scroll until I see the \"([^\"]*)\" text$")
    public void iScrollUntilISeeTheText2(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I scroll down$")
    public void iScrollDown() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I scroll up$")
    public void iScrollUp() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I scroll up until I see the \"([^\"]*)\" text$")
    public void iScrollUpUntilISeeTheText(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I see a button$")
    public void iSeeAButton() throws Throwable {
        AdbTestHelper.INSTANCE.waitForUiNode(uiNode ->
                uiNode.adbUiNodeMatches("button"));
    }

    @Then("^I see a large image$")
    public void iSeeALargeImage() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I see the \"([^\"]*)\" text$")
    public void iSeeTheText(String text) throws Throwable {
        AdbTestHelper.INSTANCE.waitForText(text, 30, TimeUnit.SECONDS);
    }

    @When("^I see the text \"([^\"]*)\"$")
    public void iSeeTheText2(String text) throws Throwable {
        AdbTestHelper.INSTANCE.waitForText(text, 30, TimeUnit.SECONDS);
    }

    @When("^I select the \"([^\"]*)\" drawer option$")
    public void iSelectTheDrawerOption(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I set the device to default settings$")
    public void iSetTheDeviceToDefaultSettings() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I should see the lock screen$")
    public void iShouldSeeTheLockScreen() throws Throwable {
        AdbTestHelper.INSTANCE.waitForUiNode(uiNode ->
                uiNode.adbUiNodeMatches("com.android.systemui:id/keyguard_status_area"));
    }

    @When("^I swipe down along the left side$")
    public void iSwipeDownAlongTheLeftSide() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I swipe down along the right side$")
    public void iSwipeDownAlongTheRightSide() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I swipe from the bottom$")
    public void iSwipeFromTheBottom() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I swipe from the left$")
    public void iSwipeFromTheLeft() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I swipe from the right$")
    public void iSwipeFromTheRight() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I swipe from the top$")
    public void iSwipeFromTheTop() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I swipe left to the next view$")
    public void iSwipeLeftToTheNextView() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I swipe open the left drawer$")
    public void iSwipeOpenTheLeftDrawer() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I swipe open the right drawer$")
    public void iSwipeOpenTheRightDrawer() throws Throwable {

        AdbTestHelper.INSTANCE.dragLeft(integer -> integer / 2, DEFAULT_EDGE_OFFSET);
    }

    @When("^I type the \"([^\"]*)\" text$")
    public void iTypeTheText(String text) throws Throwable {

        AdbTestHelper.INSTANCE.typeText(text);
    }

    @When("^I swipe up$")
    public void swipeUp() throws Throwable {

        AdbTestHelper.INSTANCE.dragUp(width -> width / 2, DEFAULT_EDGE_OFFSET);
    }

    @When("^I swipe up along the left side$")
    public void swipeUpAlongTheLeftSide() throws Throwable {

        AdbTestHelper.INSTANCE.dragUp(width -> width / 3, DEFAULT_EDGE_OFFSET);
    }

    @When("^I swipe up along the right side$")
    public void swipeUpAlongTheRightSide() throws Throwable {

        AdbTestHelper.INSTANCE.dragUp(width -> width - (width / 3), DEFAULT_EDGE_OFFSET);
    }

    @Then("^I take a screenshot$")
    public void takeScreenshot() throws Throwable {

        AdbTestHelper.INSTANCE.takeScreenshot();
    }

    @When("^I touch the menu dropdown$")
    public void iTouchTheMenuDropdown() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I touch the \"([^\"]*)\" text$")
    public void iTouchTheText(String text) throws Throwable {

        AdbTestHelper.INSTANCE.touchText(text);
    }

    @Then("^I wait$")
    public void iWait() throws Throwable {

        AdbTestHelper.INSTANCE.waitSeconds(3);
    }

    @Then("^I wait for the \"([^\"]*)\" text$")
    public void iWaitForTheText(String text) throws Throwable {
        AdbTestHelper.INSTANCE.waitForText(text, 30, TimeUnit.SECONDS);
    }

    @Then("^I wait for the \"([^\"]*)\" text to disappear$")
    public void iWaitForTheTextToDisappear(String text) throws Throwable {
        AdbTestHelper.INSTANCE.waitForTextToDisappear(text);
    }

    @Then("^I wait for \"([^\"]*)\" to appear$")
    public void iWaitForToAppear(String text) throws Throwable {
        AdbTestHelper.INSTANCE.waitForText(text, 30, TimeUnit.SECONDS);
    }

    @Then("^I wait up to (\\d+) seconds for \"([^\"]*)\" to appear$")
    public void iWaitUpToSecondsForToAppear(int seconds, String text) throws Throwable {
        AdbTestHelper.INSTANCE.waitForText(text, seconds, TimeUnit.SECONDS);
    }

    @Then("^I wait up to (\\d+) seconds to see \"([^\"]*)\"$")
    public void iWaitUpToSecondsToSee(int seconds, String text) throws Throwable {
        AdbTestHelper.INSTANCE.waitForText(text, seconds, TimeUnit.SECONDS);
    }

    @When("^I install the apk at \"([^\"]*)\"$")
    public void installApk(String apkPath) throws Throwable {
        AdbTestHelper.INSTANCE.installApk(apkPath);
    }

    @When("^I launch the app \"([^\"]*)\"$")
    public void launchApp(String packageName) throws Throwable {
        AdbTestHelper.INSTANCE.launchApp(packageName);
    }

    @When("^I launch the url \"([^\"]*)\"$")
    public void launchUrl(String url) throws Throwable {
        AdbTestHelper.INSTANCE.launchUrl(url);
    }

    @When("^I click the floating action button$")
    public void tapFloatingActionButton() throws Throwable {
        AdbTestHelper.INSTANCE.touchUiNode(uiNode ->
                uiNode.adbUiNodeMatches("floatingaction"));
    }

    @When("^I turn the screen on$")
    public void turnScreenOn() throws Throwable {
        AdbTestHelper.INSTANCE.turnScreenOn();
    }

    @When("^I uninstall the package \"([^\"]*)\"$")
    public void uninstallPackage(String packageName) throws Throwable {
        AdbTestHelper.INSTANCE.uninstallPackage(packageName);
    }

    @When("^I update the app with the apk at \"([^\"]*)\"$")
    public void updateApk(String apkPath) throws Throwable {
        AdbTestHelper.INSTANCE.updateApk(apkPath);
    }

    @Then("^I wait for (\\d+) seconds$")
    public void waitSeconds(int waitSeconds) throws Throwable {
        AdbTestHelper.INSTANCE.waitSeconds(waitSeconds);
    }
}
