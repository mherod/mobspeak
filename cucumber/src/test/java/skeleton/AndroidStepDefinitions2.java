package skeleton;

import junit.framework.AssertionFailedError;

import java.util.List;
import java.util.concurrent.TimeUnit;

import co.herod.adbwrapper.testing.AndroidStepDefDelegate;
import co.herod.adbwrapper.testing.AndroidTestHelper;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AndroidStepDefinitions2 {

    private final AndroidTestHelper androidTestHelper;

    public AndroidStepDefinitions2() {
        androidTestHelper = new AndroidStepDefDelegate();
    }

    @Given("^I am on the \"([^\"]*)\" activity$")
    public void assertActivityName(String activityName) throws Throwable {
        androidTestHelper.assertActivityName(activityName);
    }

    @Then("^I am not on the \"([^\"]*)\" activity$")
    public void assertNotActivityName(String activityName) throws Throwable {
        androidTestHelper.assertNotActivityName(activityName);
    }

    @Given("^I have a device with power at least (\\d+)$")
    public void connectDeviceAssertPower(int minPower) throws Throwable {

        androidTestHelper.connectDevice();
        androidTestHelper.assertPower(minPower);
    }

    @Then("^I dismiss the dialog$")
    public void dismissDialog() throws Throwable {
        androidTestHelper.dismissDialog();
    }

    @When("^I close the navigation drawer$")
    public void iCloseTheNavigationDrawer() throws Throwable {
        androidTestHelper.closeLeftDrawer();
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
        androidTestHelper.failOnText(text, 5, TimeUnit.SECONDS);
    }

    @Then("^I do not see the text \"([^\"]*)\"$")
    public void iDoNotSeeTheText2(String text) throws Throwable {
        androidTestHelper.failOnText(text, 5, TimeUnit.SECONDS);
    }

    @Then("^I do not see the text \"([^\"]*)\" disappear$")
    public void iDoNotSeeTheTextDisappear(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I drag down$")
    public void dragDown() throws Throwable {
        androidTestHelper.dragDown(width -> width / 3);
    }

    @When("^I drag down along the left side$")
    public void dragDownAlongTheLeftSide() throws Throwable {
        androidTestHelper.dragDown(width -> width / 3);
    }

    @When("^I drag down along the right side$")
    public void iDragDownAlongTheRightSide() throws Throwable {
        androidTestHelper.dragDown(width -> width - (width / 3));
    }

    @Then("^I drag down from the left side$")
    public void dragDownFromTheLeftSide() throws Throwable {
        androidTestHelper.dragDown(width -> width / 3);
    }

    @Then("^I drag to scroll down$")
    public void iDragToScrollDown() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I drag to scroll up$")
    public void iDragToScrollUp() throws Throwable {
        androidTestHelper.dragUp(width -> width / 2);
    }

    @When("^I drag up$")
    public void dragUp() throws Throwable {
        androidTestHelper.dragUp(width -> width / 2);
    }

    @When("^I drag up along the left side$")
    public void dragUpAlongTheLeftSide() throws Throwable {
        androidTestHelper.dragUp(width -> width / 3);
    }

    @When("^I drag up along the right side$")
    public void iDragUpAlongTheRightSide() throws Throwable {
        androidTestHelper.dragUp(width -> width - (width / 3));
    }

    @Then("^I drag up from the left side$")
    public void iDragUpFromTheLeftSide() throws Throwable {
        androidTestHelper.dragUp(width -> width / 3);
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
        androidTestHelper.backButton();
    }

    @Given("^I have a connected device$")
    public void iHaveAConnectedDevice() throws Throwable {
        androidTestHelper.connectDevice();
    }

    @Then("^I have a connected device with the package \"([^\"]*)\" version \"([^\"]*)\"$")
    public void iHaveAConnectedDeviceWithThePackageVersion(String packageName, String versionName) throws Throwable {

        final List<String> packages = androidTestHelper.getInstalledPackages();

        if (!packages.contains(packageName)) {
            throw new AssertionFailedError("Packages list did not contain " + packageName);
        }

        // TODO check version

        if (!androidTestHelper.installedPackageIsVersion(packageName, versionName)) {
            throw new AssertionFailedError("Package was not correct version");
        }

    }

    @Then("^I have a connected device without the package \"([^\"]*)\"$")
    public void iHaveAConnectedDeviceWithoutThePackage(String packageName) throws Throwable {

        final List<String> packages = androidTestHelper.getInstalledPackages();

        if (packages.contains(packageName)) {
            throw new AssertionFailedError("Packages list contained " + packageName);
        }
    }

    @When("^I login with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void iLoginWithUsernameAndPassword(String arg0, String arg1) throws Throwable {
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

    @Then("^I scroll down$")
    public void iScrollDown() throws Throwable {
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
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I see a large image$")
    public void iSeeALargeImage() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I see the \"([^\"]*)\" text$")
    public void iSeeTheText(String text) throws Throwable {
        androidTestHelper.waitForText(text);
    }

    @When("^I see the text \"([^\"]*)\"$")
    public void iSeeTheText2(String text) throws Throwable {
        androidTestHelper.waitForText(text);
    }

    @When("^I select the \"([^\"]*)\" drawer option$")
    public void iSelectTheDrawerOption(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I should see the lock screen$")
    public void iShouldSeeTheLockScreen() throws Throwable {
        androidTestHelper.waitForUiNode(adbUiNode -> adbUiNode.toString()
                .contains("com.android.systemui:id/keyguard_status_area"));
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
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I swipe up$")
    public void swipeUp() throws Throwable {
        androidTestHelper.dragUp(width -> width / 2);
    }

    @When("^I swipe up along the left side$")
    public void swipeUpAlongTheLeftSide() throws Throwable {
        androidTestHelper.dragUp(width -> width / 3);
    }

    @When("^I swipe up along the right side$")
    public void swipeUpAlongTheRightSide() throws Throwable {
        androidTestHelper.dragUp(width -> width - (width / 3));
    }

    @Then("^I take a screenshot$")
    public void takeScreenshot() throws Throwable {
        androidTestHelper.takeScreenshot();
    }

    @When("^I touch the menu dropdown$")
    public void iTouchTheMenuDropdown() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I touch the \"([^\"]*)\" text$")
    public void iTouchTheText(String text) throws Throwable {
        androidTestHelper.touchText(text);
    }

    @Then("^I wait$")
    public void iWait() throws Throwable {
        androidTestHelper.waitSeconds(3);
    }

    @Then("^I wait for the \"([^\"]*)\" text$")
    public void iWaitForTheText(String text) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I wait for the \"([^\"]*)\" text to disappear$")
    public void iWaitForTheTextToDisappear(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I wait for \"([^\"]*)\" to appear$")
    public void iWaitForToAppear(String text) throws Throwable {
        androidTestHelper.waitForText(text, 5, TimeUnit.SECONDS);
    }

    @Then("^I wait up to (\\d+) seconds for \"([^\"]*)\" to appear$")
    public void iWaitUpToSecondsForToAppear(int seconds, String text) throws Throwable {
        androidTestHelper.waitForText(text, seconds, TimeUnit.SECONDS);
    }

    @Then("^I wait up to (\\d+) seconds to see \"([^\"]*)\"$")
    public void iWaitUpToSecondsToSee(int seconds, String text) throws Throwable {
        androidTestHelper.waitForText(text, seconds, TimeUnit.SECONDS);
    }

    @When("^I install the apk at \"([^\"]*)\"$")
    public void installApk(String apkPath) throws Throwable {
        androidTestHelper.installApk(apkPath);
    }

    @When("^I launch the app \"([^\"]*)\"$")
    public void launchApp(String packageName) throws Throwable {
        androidTestHelper.launchApp(packageName);
    }

    @When("^I launch the url \"([^\"]*)\"$")
    public void launchUrl(String url) throws Throwable {
        androidTestHelper.launchUrl(url);
    }

    @When("^I click the floating action button$")
    public void tapFloatingActionButton() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I turn the screen on$")
    public void turnScreenOn() throws Throwable {
        androidTestHelper.turnScreenOn();
    }

    @When("^I uninstall the package \"([^\"]*)\"$")
    public void uninstallPackage(String packageName) throws Throwable {
        androidTestHelper.uninstallPackage(packageName);
    }

    @When("^I update the app with the apk at \"([^\"]*)\"$")
    public void updateApk(String apkPath) throws Throwable {
        androidTestHelper.updateApk(apkPath);
    }

    @Then("^I wait for (\\d+) seconds$")
    public void waitSeconds(int waitSeconds) throws Throwable {
        androidTestHelper.waitSeconds(waitSeconds);
    }
}
