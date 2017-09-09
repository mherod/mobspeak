package co.herod.adbwrapper.testing;

import org.jetbrains.annotations.Nullable;

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

    void assertValidApk(@Nullable String apkPath);

    void backButton();

    void closeLeftDrawer();

    void connectDevice();

    void dismissDialog();

    void dragDown(Function1<Integer, Integer> widthFunction);

    void dragUp(Function1<Integer, Integer> widthFunction);

    void failOnText(String text);

    void failOnText(String text, int timeout, TimeUnit timeUnit);

    List<String> getInstalledPackages();

    String getPackageVersionName(String packageName);

    void installApk(String apkPath);

    boolean installedPackageIsVersion(String packageName, String versionName);

    void launchApp(String packageName);

    void launchUrl(String url);

    void takeScreenshot();

    void touchText(String text);

    void assertScreenOn();

    void assertScreenOff();

    void turnScreenOn();

    void turnScreenOff();

    void uninstallPackage(String packageName);

    void updateApk(String apkPath);

    void waitForText(String text);

    void waitForText(String text, int timeout, TimeUnit timeUnit);

    void waitForUiNode(Predicate<AdbUiNode> adbUiNodePredicate);

    void waitSeconds(int waitSeconds);
}
