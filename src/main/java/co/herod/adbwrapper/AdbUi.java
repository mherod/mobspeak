package co.herod.adbwrapper;

import co.herod.adbwrapper.model.AdbDevice;
import co.herod.adbwrapper.rx.FixedDurationTransformer;
import co.herod.adbwrapper.rx.ResultChangeFixedDurationTransformer;
import co.herod.adbwrapper.util.StringUtils;
import co.herod.adbwrapper.util.UiHierarchyHelper;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("WeakerAccess")
public class AdbUi {

    public static Observable<String> streamUiHierarchyUpdates(final AdbDevice connectedAdbDevice) {

        return streamUiNodeUpdates(connectedAdbDevice)
                .repeat()
                .filter(StringUtils::isNotEmpty);
    }

    public static Observable<String> streamUiHierarchyUpdates(final AdbDevice connectedAdbDevice, final String packageIdentifier) {

        return streamUiNodeUpdates(connectedAdbDevice, packageIdentifier)
                .repeat()
                .filter(StringUtils::isNotEmpty);
    }

    private static Observable<String> streamUiNodeUpdates(final AdbDevice adbDevice) {

        return ProcessHelper.observableProcess(AdbProcesses.dumpUiHierarchyProcess(adbDevice))
                .doOnNext(s -> Observable.fromCallable(() -> ScreenshotHelper.screenshot(adbDevice)).blockingSubscribe())
                .map(StringUtils::extractXmlString)
                .compose(new ResultChangeFixedDurationTransformer())
                .doOnNext(System.out::println)
                .compose(UiHierarchyHelper::uiXmlToNodes)
                .compose(new FixedDurationTransformer(1, TimeUnit.DAYS))
                .onErrorReturn(throwable -> {
                    throwable.printStackTrace();
                    return "";
                });
    }

    @SuppressWarnings("SameParameterValue")
    private static Observable<String> streamUiNodeUpdates(final AdbDevice connectedAdbDevice, final String packageIdentifier) {
        return streamUiNodeUpdates(connectedAdbDevice).filter(s -> UiHierarchyHelper.isPackage(packageIdentifier, s));
    }
}
