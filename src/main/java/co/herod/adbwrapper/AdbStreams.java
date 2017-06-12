package co.herod.adbwrapper;

import co.herod.adbwrapper.model.AdbUiNode;
import co.herod.adbwrapper.rx.FixedDurationTransformer;
import co.herod.adbwrapper.rx.ResultChangeFixedDurationTransformer;
import co.herod.adbwrapper.util.StringUtils;
import co.herod.adbwrapper.util.UiHierarchyHelper;
import io.reactivex.Observable;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class AdbStreams {

    public static Observable<String> streamAdbCommands() {
        return AdbBusManager.throttledBus().filter(AdbFilters::isAdbInput);
    }

    public static Observable<String> streamUiHierarchyUpdates() {
        return AdbBusManager.throttledBus().filter(AdbFilters::isUiDumpOutput);
    }

    public static Observable<AdbUiNode> streamUiNodeChanges() {

        return streamUiHierarchyUpdates()
                .map(StringUtils::extractXmlString)
                .compose(new ResultChangeFixedDurationTransformer())
                .compose(UiHierarchyHelper::uiXmlToNodes)
                .onErrorReturn(throwable -> "")
                .filter(StringUtils::isNotEmpty)
                .compose(new FixedDurationTransformer(1, TimeUnit.DAYS))
                .map(AdbUiNode::new);
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
