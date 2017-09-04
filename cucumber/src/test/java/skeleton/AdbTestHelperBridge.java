package skeleton;

import co.herod.adbwrapper.AdbTestHelper;

class AdbTestHelperBridge {

    private final AndroidStepDefinitions androidStepDefinitions;

    AdbTestHelperBridge(AndroidStepDefinitions androidStepDefinitions) {
        this.androidStepDefinitions = androidStepDefinitions;
    }

    void failOnText(String text) {
        AdbTestHelper.INSTANCE.failOnText(androidStepDefinitions.getConnectedAdbDevice(), text);
    }

    void waitForText(String text) {
        AdbTestHelper.INSTANCE.waitForText(androidStepDefinitions.getConnectedAdbDevice(), text);
    }
}