package skeleton;

import co.herod.adbwrapper.AdbDeviceActions;
import co.herod.adbwrapper.AdbDeviceManager;
import co.herod.adbwrapper.AdbDeviceProperties;
import co.herod.adbwrapper.model.AdbDevice;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AdbStepdefs {

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
}
