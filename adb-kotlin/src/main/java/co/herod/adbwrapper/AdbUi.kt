package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.AdbUiHierarchy
import co.herod.adbwrapper.model.AdbUiNode
import co.herod.adbwrapper.rx.FixedDurationTransformer
import co.herod.adbwrapper.rx.ResultChangeFixedDurationTransformer
import co.herod.adbwrapper.util.UiHierarchyHelper
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

object AdbUi {

    fun startStreamingUiHierarchy(adbDevice: AdbDevice): Observable<AdbUiNode> = Adb.dumpUiHierarchy(adbDevice)
            .map { it.substring(it.indexOf('<'), it.lastIndexOf('>') + 1) }
            .compose(ResultChangeFixedDurationTransformer())
            // .doOnNext(s -> screenshotBlocking(adbDevice, true))
            .map { AdbUiHierarchy(it, adbDevice) }
            .doOnEach(AdbBusManager.getAdbUiHierarchyBus())
            .map { it.xmlString }
            .compose { UiHierarchyHelper.uiXmlToNodes(it) }
            .map { AdbUiNode(it) }
            .doOnEach(AdbBusManager.getAdbUiNodeBus())
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.newThread())

    fun fetchUiHierarchy(adbDevice: AdbDevice): Observable<AdbUiNode> = Adb.dumpUiHierarchy(adbDevice)
            .map { it.substring(it.indexOf('<'), it.lastIndexOf('>') + 1) }
            .map { AdbUiHierarchy(it, adbDevice) }
            .map { it.xmlString }
            .compose { UiHierarchyHelper.uiXmlToNodes(it) }
            .map { AdbUiNode(it) }
            .doOnEach(AdbBusManager.getAdbUiNodeBus())

    private fun streamUiNodes() = streamUiNodeStringsInternal().map { AdbUiNode(it) }

    fun AdbDevice.streamUiNodeStrings() = streamUiNodes().map { it.toString() }

    fun AdbDevice.streamUiNodes(packageIdentifier: String) = streamUiNodeStrings(packageIdentifier).map { AdbUiNode(it) }

    private fun streamUiNodeStrings(packageIdentifier: String) =
            streamUiNodeStringsInternal().filter { s -> UiHierarchyHelper.isPackage(packageIdentifier, s) }

    private fun streamUiNodeStringsInternal() = AdbBusManager.getAdbUiNodeBus()
            .map { it.toString() }
            .compose(FixedDurationTransformer(1, TimeUnit.DAYS))
            .onErrorReturn { throwable -> throwable.printStackTrace(); "" }
            .repeat()
            .filter { !it.trim { it <= ' ' }.isEmpty() }

    internal fun pullScreenCapture(adbDevice: AdbDevice) {
        with(Adb) {
            now(adbDevice, "shell screencap -p /sdcard/screen.png")
            now(adbDevice, "pull /sdcard/screen.png")
            now(adbDevice, "shell rm /sdcard/screen.png")
        }
    }

//    private fun AdbDevice.screenshotBlocking(ignoreCache: Boolean) {
//
//        Observable.fromCallable<File> { ScreenshotHelper.takeScreenshot(this, ignoreCache) }
//                .timeout(10, TimeUnit.SECONDS)
//                .blockingSubscribe()
//    }
}
