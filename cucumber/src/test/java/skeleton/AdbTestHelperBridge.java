package skeleton;

import co.herod.adbwrapper.AdbTestHelper;
import co.herod.adbwrapper.model.AdbDevice;

class AdbTestHelperBridge {

    private final AndroidStepDefinitions androidStepDefinitions;

    AdbTestHelperBridge(AndroidStepDefinitions androidStepDefinitions) {
        this.androidStepDefinitions = androidStepDefinitions;
    }

    private AdbDevice getConnectedAdbDevice() {
        return androidStepDefinitions.getConnectedAdbDevice();
    }

    void failOnText(String text) {
        AdbTestHelper.INSTANCE.failOnText(getConnectedAdbDevice(), text);
    }

    void waitForText(String text) {
        AdbTestHelper.INSTANCE.waitForText(getConnectedAdbDevice(), text);
    }
}