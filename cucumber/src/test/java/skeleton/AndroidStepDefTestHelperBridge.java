package skeleton;

import java.util.Objects;

import co.herod.adbwrapper.AdbPackageManager;
import co.herod.adbwrapper.AdbTestHelper;
import co.herod.adbwrapper.model.AdbDevice;

class AndroidStepDefTestHelperBridge {

    private final AndroidStepDefinitions androidStepDefinitions;

    AndroidStepDefTestHelperBridge(AndroidStepDefinitions androidStepDefinitions) {
        this.androidStepDefinitions = androidStepDefinitions;
    }

    private AdbDevice getConnectedAdbDevice() {
        return androidStepDefinitions.getConnectedAdbDevice();
    }

    void failOnText(String text) {
        AdbTestHelper.INSTANCE.failOnText(getConnectedAdbDevice(), text);
    }

    boolean installedPackageIsVersion(String packageName, String versionName) {
        return Objects.equals(getPackageVersionName(packageName), versionName);
    }

    void waitForText(String text) {
        AdbTestHelper.INSTANCE.waitForText(getConnectedAdbDevice(), text);
    }

    private String getPackageVersionName(String packageName) {
        return AdbPackageManager.INSTANCE.getPackageVersionName(getConnectedAdbDevice(), packageName);
    }
}