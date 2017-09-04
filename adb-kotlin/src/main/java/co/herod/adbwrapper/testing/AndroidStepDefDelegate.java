package co.herod.adbwrapper.testing;

import java.util.List;
import java.util.Objects;

import co.herod.adbwrapper.AdbDeviceActions;
import co.herod.adbwrapper.AdbDeviceManager;
import co.herod.adbwrapper.AdbPackageManager;
import co.herod.adbwrapper.model.AdbDevice;

public class AndroidStepDefDelegate {

    private AdbDevice adbDevice = null;

    public void assertActivityName(String activityName) {
        // TODO
    }

    public void assertPower(int minPower) {
        // TODO
    }

    public void connectDevice() {
        setAdbDevice(getConnectedDevice());
    }

    public AdbDevice getConnectedDevice() {
        return AdbDeviceManager.INSTANCE.getConnectedDevice();
    }

    public void failOnText(String text) {

        AdbTestHelper.INSTANCE.failOnText(getAdbDevice(), text);
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
        AdbTestHelper.INSTANCE.waitForText(getAdbDevice(), text);
    }

    public void waitSeconds(int waitSeconds) {

        try {
            Thread.sleep(waitSeconds * 1000);
        } catch (InterruptedException ignored) {
        }
    }
}