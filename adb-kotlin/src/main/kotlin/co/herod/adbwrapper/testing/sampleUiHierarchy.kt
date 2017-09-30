package co.herod.adbwrapper.testing

import co.herod.adbwrapper.model.UiHierarchy
import co.herod.adbwrapper.ui.sourceUiHierarchy
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun AdbDeviceTestHelper.sampleUiHierarchy(): Observable<UiHierarchy> = sourceUiHierarchy().sample(100, TimeUnit.SECONDS)