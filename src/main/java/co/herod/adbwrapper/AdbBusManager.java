package co.herod.adbwrapper;

import co.herod.adbwrapper.model.AdbUiHierarchy;
import co.herod.adbwrapper.model.AdbUiNode;
import co.herod.adbwrapper.util.Utils;
import io.reactivex.Observable;
import org.jetbrains.annotations.NotNull;

/**
 * Created by matthewherod on 23/04/2017.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class AdbBusManager {

    @NotNull
    public static final BusSubject<String> ADB_BUS = new MainBusSubject();

    @NotNull
    public static final BusSubject<AdbUiHierarchy> ADB_UI_HIERARCHY_BUS = new AdbUiHierarchyBus();

    @NotNull
    public static final BusSubject<AdbUiNode> ADB_UI_NODE_BUS = new AdbUiNodeBus();

    private AdbBusManager() {
    }

    static Observable<String> throttledBus() {
        return ADB_BUS.concatMap(Utils::throttleOutput);
    }

    private static class MainBusSubject extends BusSubject<String> {

    }

    private static class AdbUiHierarchyBus extends BusSubject<AdbUiHierarchy> {

    }

    private static class AdbUiNodeBus extends BusSubject<AdbUiNode> {

    }
}
