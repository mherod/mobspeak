package co.herod.adbwrapper;

import co.herod.adbwrapper.model.AdbDevice;
import co.herod.adbwrapper.model.AdbUiNode;
import co.herod.adbwrapper.rx.FixedDurationTransformer;
import co.herod.adbwrapper.rx.ResultChangeFixedDurationTransformer;
import co.herod.adbwrapper.util.StringUtils;
import co.herod.adbwrapper.util.UiHierarchyHelper;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

@SuppressWarnings({"unused", "WeakerAccess"})
public class AdbUi {

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

        return Adb.dumpUiHierarchy(adbDevice)
                .doOnNext(s -> Observable.fromCallable(() -> ScreenshotHelper.screenshot(adbDevice)).blockingSubscribe())
                .map(StringUtils::extractXmlString)
                .compose(new ResultChangeFixedDurationTransformer())
                .doOnNext(System.out::println)
                .compose(UiHierarchyHelper::uiXmlToNodes)
                .compose(new FixedDurationTransformer(1, TimeUnit.DAYS))
                .onErrorReturn(throwable -> {
                    throwable.printStackTrace();
                    return "";
                })
                .repeat()
                .filter(StringUtils::isNotEmpty);
    }
}
