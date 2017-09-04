package skeleton;

import co.herod.adbwrapper.AdbDeviceActions;
import co.herod.adbwrapper.AdbDeviceManager;
import co.herod.adbwrapper.model.AdbDevice;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AndroidStepDefinitions {

    private AdbDevice connectedAdbDevice = null;

    @Given("^I have a device with power at least (\\d+)$")
    public void iHaveADeviceWithPowerAtLeast(int minPower) throws Throwable {

        if (connectedAdbDevice == null) {
            connectedAdbDevice = AdbDeviceManager.INSTANCE.getConnectedDevice();
        }
    }

    @When("^I turn the screen on$")
    public void iTurnTheScreenOn() throws Throwable {

        AdbDeviceActions.INSTANCE.turnDeviceScreenOn(connectedAdbDevice);
    }

    @Then("^I should see the lock screen$")
    public void iShouldSeeTheLockScreen() throws Throwable {

    }

    @When("^I login with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void iLoginWithUsernameAndPassword(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^I am ready$")
    public void iAmReady() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I navigate to \"([^\"]*)\"$")
    public void iNavigateTo(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I see the \"([^\"]*)\" text$")
    public void iSeeTheText(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I see the text \"([^\"]*)\"$")
    public void iSeeTheText2(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I touch the \"([^\"]*)\" text$")
    public void iTouchTheText(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I swipe left to the next view$")
    public void iSwipeLeftToTheNextView() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I open the navigation drawer$")
    public void iOpenTheNavigationDrawer() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I take a screenshot$")
    public void iTakeAScreenshot() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I close the navigation drawer$")
    public void iCloseTheNavigationDrawer() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I do not see the text \"([^\"]*)\"$")
    public void iDoNotSeeTheText3(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I enter \"([^\"]*)\" into input field number (\\d+)$")
    public void iEnterIntoInputFieldNumber(String arg0, int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I wait for \"([^\"]*)\" to appear$")
    public void iWaitForToAppear(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I wait up to (\\d+) seconds for \"([^\"]*)\" to appear$")
    public void iWaitUpToSecondsForToAppear(int arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I wait for (\\d+) seconds$")
    public void iWaitForSeconds(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^I am on the \"([^\"]*)\" activity$")
    public void iAmOnTheActivity(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^I am not logged in$")
    public void iAmNotLoggedIn() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I attempt login with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void iAttemptLoginWithUsernameAndPassword(String arg0, String arg1) throws Throwable {
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

    @Then("^I scroll until I see the text \"([^\"]*)\"$")
    public void iScrollUntilISeeTheText(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I click the left switcher$")
    public void iClickTheLeftSwitcher() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I click the right switcher$")
    public void iClickTheRightSwitcher() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I drag to scroll up$")
    public void iDragToScrollUp() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I wait up to (\\d+) seconds to see \"([^\"]*)\"$")
    public void iWaitUpToSecondsToSee(int arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I scroll up until I see the \"([^\"]*)\" text$")
    public void iScrollUpUntilISeeTheText(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I go back$")
    public void iGoBack() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I scroll until I see the \"([^\"]*)\" text$")
    public void iScrollUntilISeeTheText2(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I click the floating action button$")
    public void iClickTheFloatingActionButton() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I dismiss the tip dialog$")
    public void iDismissTheTipDialog() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I enter \"([^\"]*)\" into \"([^\"]*)\"$")
    public void iEnterInto(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I drag to scroll down$")
    public void iDragToScrollDown() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I wait$")
    public void iWait() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I drag up from the left side$")
    public void iDragUpFromTheLeftSide() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I drag down from the left side$")
    public void iDragDownFromTheLeftSide() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I enter text \"([^\"]*)\" into field with id \"([^\"]*)\"$")
    public void iEnterTextIntoFieldWithId(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I touch the menu dropdown$")
    public void iTouchTheMenuDropdown() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I wait for delete of \"([^\"]*)\"$")
    public void iWaitForDeleteOf(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I wait for the \"([^\"]*)\" text$")
    public void iWaitForTheText(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I wait for the \"([^\"]*)\" text to disappear$")
    public void iWaitForTheTextToDisappear(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I do not see the \"([^\"]*)\" text$")
    public void iDoNotSeeTheText(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I do not see the text \"([^\"]*)\"$")
    public void iDoNotSeeTheText2(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I do not see the text \"([^\"]*)\" disappear$")
    public void iDoNotSeeTheTextDisappear(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I swipe from the left$")
    public void iSwipeFromTheLeft() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I swipe from the top$")
    public void iSwipeFromTheTop() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I swipe from the bottom$")
    public void iSwipeFromTheBottom() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I swipe from the right$")
    public void iSwipeFromTheRight() throws Throwable {
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

    @When("^I select the \"([^\"]*)\" drawer option$")
    public void iSelectTheDrawerOption(String arg0) throws Throwable {
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
}