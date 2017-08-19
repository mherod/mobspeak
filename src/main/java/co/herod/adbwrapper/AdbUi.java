package co.herod.adbwrapper;

import co.herod.adbwrapper.model.AdbDevice;
import co.herod.adbwrapper.model.AdbUiHierarchy;
import co.herod.adbwrapper.model.AdbUiNode;
import co.herod.adbwrapper.rx.FixedDurationTransformer;
import co.herod.adbwrapper.rx.ResultChangeFixedDurationTransformer;
import co.herod.adbwrapper.util.StringUtils;
import co.herod.adbwrapper.util.UiHierarchyHelper;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

@SuppressWarnings({"unused", "WeakerAccess"})
public class AdbUi {

    public static Observable<AdbUiNode> startStreamingUiHierarchy(final AdbDevice adbDevice) {

        return Adb.dumpUiHierarchy(adbDevice)
                .map(StringUtils::extractXmlString)
                .compose(new ResultChangeFixedDurationTransformer())
                // .doOnNext(s -> screenshotBlocking(adbDevice, true))
                .map(s -> new AdbUiHierarchy(s, adbDevice))
                .doOnEach(AdbBusManager.ADB_UI_HIERARCHY_BUS)
                .map(AdbUiHierarchy::getXmlString)
                .compose(UiHierarchyHelper::uiXmlToNodes)
                .map(AdbUiNode::new)
                .doOnEach(AdbBusManager.ADB_UI_NODE_BUS)
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread());
    }

    public static Observable<String> streamUiNodeStrings(final AdbDevice connectedAdbDevice) {
        return streamUiNodes(connectedAdbDevice).map(AdbUiNode::toString);
    }

    public static Observable<AdbUiNode> streamUiNodes(final AdbDevice connectedAdbDevice, final String packageIdentifier) {
        return streamUiNodeStrings(connectedAdbDevice, packageIdentifier).map(AdbUiNode::new);
    }

    public static Observable<AdbUiNode> streamUiNodes(final AdbDevice connectedAdbDevice) {
        return streamUiNodeStringsInternal(connectedAdbDevice).map(AdbUiNode::new);
    }

    private static Observable<String> streamUiNodeStrings(final AdbDevice connectedAdbDevice, final String packageIdentifier) {
        return streamUiNodeStringsInternal(connectedAdbDevice).filter(s -> UiHierarchyHelper.isPackage(packageIdentifier, s));
    }

    private static Observable<String> streamUiNodeStringsInternal(final AdbDevice adbDevice) {

        return AdbBusManager.ADB_UI_NODE_BUS
                .map(AdbUiNode::toString)
                .compose(new FixedDurationTransformer(1, TimeUnit.DAYS))
                .onErrorReturn(throwable -> {
                    throwable.printStackTrace();
                    return "";
                })
                .repeat()
                .filter(StringUtils::isNotEmpty);
    }

    static void pullScreenCapture(final AdbDevice adbDevice) {
        Adb.blocking(adbDevice, "shell screencap -p /sdcard/screen.png");
        Adb.blocking(adbDevice, "pull /sdcard/screen.png");
        Adb.blocking(adbDevice, "shell rm /sdcard/screen.png");
    }

    private static void screenshotBlocking(final AdbDevice adbDevice, final boolean ignoreCache) {

        Observable.fromCallable(() -> ScreenshotHelper.screenshot(adbDevice, ignoreCache))
                .timeout(10, TimeUnit.SECONDS)
                .blockingSubscribe();
    }
}
