package co.herod.adbwrapper;

import co.herod.adbwrapper.rx.FixedDurationTransformer;
import co.herod.adbwrapper.rx.ResultChangeFixedDurationTransformer;
import co.herod.adbwrapper.util.StringUtils;
import co.herod.adbwrapper.util.UiHierarchyHelper;
import io.reactivex.Observable;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

@SuppressWarnings({"WeakerAccess", "unused"})
public class AdbStreams {

    public static Observable<String> streamAdbCommands() {
        return AdbBus.getThrottledBus().filter(AdbFilters::isAdbInput);
    }

    public static Observable<String> streamUiHierarchyUpdates() {
        return AdbBus.getThrottledBus().filter(AdbFilters::isUiDumpOutput);
    }

    public static Observable<String> streamUiNodeChanges() {

        return streamUiHierarchyUpdates()
                .map(StringUtils::extractXmlString)
                .compose(new ResultChangeFixedDurationTransformer())
                // .doOnNext(System.out::println)
                .compose(UiHierarchyHelper::uiXmlToNodes)
                .compose(new FixedDurationTransformer(1, TimeUnit.DAYS))
                .onErrorReturn(throwable -> "");
    }

    static class AdbFilters {

        static boolean isAdbInput(@NotNull final String s) {
            return s.startsWith("adb ") && !s.trim().equals("Killed");
        }

        static boolean isUiDumpOutput(@NotNull final String s) {
            return s.contains("<node") && s.contains("bounds=");
        }

    }
}
