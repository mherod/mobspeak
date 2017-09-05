package co.herod.adbwrapper.testing;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import co.herod.adbwrapper.AdbDeviceActions;
import co.herod.adbwrapper.AdbDeviceManager;
import co.herod.adbwrapper.AdbPackageManager;
import co.herod.adbwrapper.model.AdbDevice;

public class AndroidStepDefDelegate implements AndroidTestHelper {

    private AdbDevice adbDevice = null;

    @Override
    public void assertActivityName(String activityName) {
        // TODO
    }

    @Override
    public void assertPower(int minPower) {
        // TODO
    }

    public void connectDevice() {
        setAdbDevice(getConnectedDevice());
    }

    public AdbDevice getConnectedDevice() {
        return AdbDeviceManager.INSTANCE.getConnectedDevice();
    }

    @Override
    public void failOnText(String text) {
        failOnText(text, 10, TimeUnit.SECONDS);
    }

    @Override
    public void failOnText(String text, int timeout, TimeUnit timeUnit) {
        AdbTestHelper.INSTANCE.failOnText(getAdbDevice(), text, timeout, timeUnit);
    }

    private AdbDevice getAdbDevice() {
        return adbDevice;
    }

    public void setAdbDevice(AdbDevice adbDevice) {
        this.adbDevice = adbDevice;
    }

    public List<String> getInstalledPackages() {
        return AdbPackageManager.INSTANCE.listPackages(adbDevice).blockingGet();
    }

    public void installApk(String apkPath) {
        AdbPackageManager.INSTANCE.installPackage(getConnectedDevice(), apkPath);
    }

    public boolean installedPackageIsVersion(String packageName, String versionName) {
        return Objects.equals(getPackageVersionName(packageName), versionName);
    }

    private String getPackageVersionName(String packageName) {
        return AdbPackageManager.INSTANCE.getPackageVersionName(getAdbDevice(), packageName);
    }

    public void launchApp(String packageName) {
        AdbPackageManager.INSTANCE.launchApp(getConnectedDevice(), packageName);
    }

    public void turnScreenOn() {
        AdbDeviceActions.INSTANCE.turnDeviceScreenOn(getConnectedDevice());
    }

    public void uninstallPackage(String packageName) {
        AdbPackageManager.INSTANCE.uninstallPackage(getConnectedDevice(), packageName);
    }

    public void updateApk(String apkPath) {
        AdbPackageManager.INSTANCE.updatePackage(getConnectedDevice(), apkPath);
    }

    public void waitForText(String text) {
        waitForText(text, 30, TimeUnit.SECONDS);
    }

    public void waitForText(String text, int timeout, TimeUnit timeUnit) {
        AdbTestHelper.INSTANCE.waitForText(getAdbDevice(), text, timeout, timeUnit);
    }

    public void waitSeconds(int waitSeconds) {

        try {
            Thread.sleep(waitSeconds * 1000);
        } catch (InterruptedException ignored) {
        }
    }
}