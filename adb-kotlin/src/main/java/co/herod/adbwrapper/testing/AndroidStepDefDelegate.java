package co.herod.adbwrapper.testing;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import co.herod.adbwrapper.model.AdbDevice;
import co.herod.adbwrapper.model.AdbUiNode;
import kotlin.jvm.functions.Function1;

public class AndroidStepDefDelegate implements AndroidTestHelper {

    @Override
    public void assertActivityName(String activityName) {
        adbTestHelper().assertActivityName(activityName);
    }

    @Override
    public void assertNotActivityName(String activityName) {
        adbTestHelper().assertNotActivityName(activityName);
    }

    @Override
    public void assertPower(int minPower) {
        adbTestHelper().assertPower(minPower);
    }

    @Override
    public void assertValidApk(@Nullable String apkPath) {
        adbTestHelper().assertValidApk(apkPath);
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
        adbTestHelper().connectDevice();
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
        adbTestHelper().failOnText(text);
    }

    @Override
    public void failOnText(String text, int timeout, TimeUnit timeUnit) {
        adbTestHelper().failOnText(getAdbDevice(), text, timeout, timeUnit);
    }

    public List<String> getInstalledPackages() {
        return adbTestHelper().getInstalledPackages();
    }

    @Override
    public String getPackageVersionName(String packageName) {
        return adbTestHelper().getPackageVersionName(packageName);
    }

    public void installApk(String apkPath) {
        adbTestHelper().installApk(apkPath);
    }

    @Override
    public boolean installedPackageIsVersion(String packageName, String versionName) {
        return adbTestHelper().installedPackageIsVersion(packageName, versionName);
    }

    @Override
    public void launchApp(String packageName) {
        adbTestHelper().launchApp(packageName);
    }

    @Override
    public void launchUrl(String url) {
        adbTestHelper().launchUrl(url);
    }

    @Override
    public void takeScreenshot() {
        adbTestHelper().takeScreenshot();
    }

    @Override
    public void touchText(String text) {
        adbTestHelper().touchText(text);
    }

    @Override
    public void assertScreenOn() {
        adbTestHelper().assertScreenOn();
    }

    @Override
    public void assertScreenOff() {
        adbTestHelper().assertScreenOff();
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
    public void waitForText(String text) {
        adbTestHelper().waitForText(text, 30, TimeUnit.SECONDS);
    }

    @Override
    public void waitForText(String text, int timeout, TimeUnit timeUnit) {
        adbTestHelper().waitForText(text, timeout, timeUnit);
    }

    @Override
    public void waitForUiNode(Predicate<AdbUiNode> adbUiNodePredicate) {
        adbTestHelper().waitForUiNode(adbUiNodePredicate);
    }

    @Override
    public void waitSeconds(int waitSeconds) {
        adbTestHelper().waitSeconds(waitSeconds);
    }

    private AdbDevice getAdbDevice() {
        return adbTestHelper().getAdbDevice();
    }

    private AdbTestHelper adbTestHelper() {
        return AdbTestHelper.INSTANCE;
    }
}