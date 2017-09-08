package co.herod.adbwrapper.testing;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import co.herod.adbwrapper.AdbDeviceManager;
import co.herod.adbwrapper.AdbPackageManager;
import co.herod.adbwrapper.model.AdbDevice;
import co.herod.adbwrapper.model.AdbUiNode;
import kotlin.jvm.functions.Function1;

public class AndroidStepDefDelegate implements AndroidTestHelper {

    private AdbDevice adbDevice = null;

    @Override
    public void assertActivityName(String activityName) {
        adbTestHelper().assertActivityName(activityName);
    }

    @Override
    public void assertPower(int minPower) {
        adbTestHelper().assertPower(minPower);
    }

    @Override
    public void backButton() {
        adbTestHelper().backButton();
    }

    @Override
    public void closeLeftDrawer() {
        adbTestHelper().closeLeftDrawer();
    }

    public void connectDevice() {
        setAdbDevice(getConnectedDevice());
    }

    @Override
    public void dismissDialog() {
        adbTestHelper().dismissDialog();
    }

    @Override
    public void dragDown(Function1<Integer, Integer> widthFunction) {
        adbTestHelper().dragDown(widthFunction);
    }

    @Override
    public void dragUp(Function1<Integer, Integer> widthFunction) {
        adbTestHelper().dragUp(widthFunction);
    }

    @Override
    public void failOnText(String text) {
        failOnText(text, 10, TimeUnit.SECONDS);
    }

    @Override
    public void failOnText(String text, int timeout, TimeUnit timeUnit) {
        adbTestHelper().failOnText(getAdbDevice(), text, timeout, timeUnit);
    }

    @Override
    public void takeScreenshot() {

    }

    @Override
    public void touchText(String text) {

    }

    @Override
    public void assertScreenOn() {

    }

    @Override
    public void assertScreenOff() {

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
        return adbTestHelper().installedPackageIsVersion(packageName, versionName);
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
        adbTestHelper().launchUrl(url);
    }

    @Override
    public void turnScreenOn() {
        adbTestHelper().turnScreenOn();
    }

    @Override
    public void turnScreenOff() {
        adbTestHelper().turnScreenOff();
    }

    @Override
    public void uninstallPackage(String packageName) {
        adbTestHelper().uninstallPackage(packageName);
    }

    @Override
    public void updateApk(String apkPath) {
        adbTestHelper().updateApk(apkPath);
    }

    @Override
    public void waitForUiNode(Predicate<AdbUiNode> adbUiNodePredicate) {
        adbTestHelper().waitForUiNode(adbUiNodePredicate);
    }

    @Override
    public void waitForText(String text) {
        waitForText(text, 30, TimeUnit.SECONDS);
    }

    @Override
    public void waitForText(String text, int timeout, TimeUnit timeUnit) {
        adbTestHelper().waitForText(getAdbDevice(), text, timeout, timeUnit);
    }

    @Override
    public void waitSeconds(int waitSeconds) {
        adbTestHelper().waitSeconds(waitSeconds);
    }

    private AdbDevice getConnectedDevice() {
        return AdbDeviceManager.INSTANCE.getConnectedDevice();
    }

    private AdbTestHelper adbTestHelper() {

        final AdbTestHelper adbTestHelper = AdbTestHelper.INSTANCE;
        adbTestHelper.setAdbDevice(getConnectedDevice());
        return adbTestHelper;
    }
}