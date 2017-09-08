package co.herod.adbwrapper.testing;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import co.herod.adbwrapper.model.AdbUiNode;
import kotlin.jvm.functions.Function1;

/**
 * Created by matthewherod on 05/09/2017.
 */

public interface AndroidTestHelper {

    void assertActivityName(String activityName);

    void assertPower(int minPower);

    void backButton();

    void closeLeftDrawer();

    void connectDevice();

    void dismissDialog();

    void dragDown(Function1<Integer, Integer> widthFunction);

    void dragUp(Function1<Integer, Integer> widthFunction);

    void failOnText(String text);

    void failOnText(String text, int timeout, TimeUnit timeUnit);

    void takeScreenshot();

    void touchText(String text);

    abstract void waitForText(String text);

    abstract void waitForText(String text, int timeout, TimeUnit timeUnit);

    List<String> getInstalledPackages();

    void installApk(String apkPath);

    abstract boolean installedPackageIsVersion(String packageName, String versionName);

    String getPackageVersionName(String packageName);

    abstract void launchApp(String packageName);

    abstract void launchUrl(String url);

    abstract void turnScreenOn();

    abstract void uninstallPackage(String packageName);

    abstract void updateApk(String apkPath);

    abstract void waitForUiNode(Predicate<AdbUiNode> adbUiNodePredicate);

    abstract void waitSeconds(int waitSeconds);
}
