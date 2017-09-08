package co.herod.adbwrapper.testing;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import co.herod.adbwrapper.AdbDeviceActions;
import co.herod.adbwrapper.AdbDeviceManager;
import co.herod.adbwrapper.AdbPackageManager;
import co.herod.adbwrapper.model.AdbDevice;
import co.herod.adbwrapper.model.AdbUiNode;
import kotlin.jvm.functions.Function1;

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

    @Override
    public void backButton() {

    }

    @Override
    public void closeLeftDrawer() {

    }

    public void connectDevice() {
        setAdbDevice(getConnectedDevice());
    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void dragDown(Function1<Integer, Integer> widthFunction) {

    }

    @Override
    public void dragUp(Function1<Integer, Integer> widthFunction) {

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

    @Override
    public void takeScreenshot() {

    }

    @Override
    public void touchText(String text) {

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

    @Override
    public boolean installedPackageIsVersion(String packageName, String versionName) {
        return Objects.equals(getPackageVersionName(packageName), versionName);
    }

    @Override
    public String getPackageVersionName(String packageName) {
        return AdbPackageManager.INSTANCE.getPackageVersionName(getAdbDevice(), packageName);
    }

    @Override
    public void launchApp(String packageName) {
        AdbPackageManager.INSTANCE.launchApp(getConnectedDevice(), packageName);
    }

    @Override
    public void launchUrl(String url) {

    }

    @Override
    public void turnScreenOn() {
        AdbDeviceActions.INSTANCE.turnDeviceScreenOn(getConnectedDevice());
    }

    @Override
    public void uninstallPackage(String packageName) {
        AdbPackageManager.INSTANCE.uninstallPackage(getConnectedDevice(), packageName);
    }

    @Override
    public void updateApk(String apkPath) {
        AdbPackageManager.INSTANCE.updatePackage(getConnectedDevice(), apkPath);
    }

    @Override
    public void waitForUiNode(Predicate<AdbUiNode> adbUiNodePredicate) {

    }

    @Override
    public void waitForText(String text) {
        waitForText(text, 30, TimeUnit.SECONDS);
    }

    @Override
    public void waitForText(String text, int timeout, TimeUnit timeUnit) {
        AdbTestHelper.INSTANCE.waitForText(getAdbDevice(), text, timeout, timeUnit);
    }

    @Override
    public void waitSeconds(int waitSeconds) {

        try {
            Thread.sleep(waitSeconds * 1000);
        } catch (InterruptedException ignored) {
        }
    }
}