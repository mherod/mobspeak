package co.herod.adbwrapper;

import co.herod.adbwrapper.rx.FixedDurationTransformer;
import co.herod.adbwrapper.rx.ResultChangeFixedDurationTransformer;
import co.herod.adbwrapper.util.StringUtils;
import co.herod.adbwrapper.util.UiHierarchyHelper;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("WeakerAccess")
class AdbStreams {

    static Observable<String> streamAdbCommands() {
        return AdbBus.getBus().filter(AdbFilters::isAdbInput);
    }

    static Observable<String> streamUiHierarchyUpdates() {
        return AdbBus.getBus().filter(AdbFilters::isUiDumpOutput);
    }

    static Observable<String> streamUiNodeChanges() {

        return streamUiHierarchyUpdates()
                .map(StringUtils::extractXmlString)
                .compose(new ResultChangeFixedDurationTransformer())
                // .doOnNext(System.out::println)
                .compose(UiHierarchyHelper::uiXmlToNodes)
                .compose(new FixedDurationTransformer(1, TimeUnit.DAYS))
                .onErrorReturn(throwable -> "");
    }

    static class AdbFilters {

        static boolean isAdbInput(String s) {
            return s.startsWith("adb ") && !s.trim().equals("Killed");
        }

        static boolean isUiDumpOutput(String s) {
            return s.contains("<node") && s.contains("bounds=");
        }

    }
}
