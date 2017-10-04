package co.herod.adbwrapper.ui

import co.herod.adbwrapper.AdbBusManager
import co.herod.adbwrapper.S.Companion.SHELL
import co.herod.adbwrapper.command
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiNode
import co.herod.adbwrapper.ui.Blah.Companion.subject
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun AdbDevice.streamUi(): Observable<UiNode> {
    return Observable.timer(100, TimeUnit.MILLISECONDS)
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.newThread())
            .flatMap { dumpUiHierarchy() }
            .repeat()
            .doOnEach(AdbBusManager._uiHierarchyBus)
            .flatMapIterable { it.uiNodes }
            .doOnEach(AdbBusManager._uiNodeBus)
            .doOnSubscribe {
                println("Subscribe of streamUi")
                AdbBusManager.uiHierarchyBusActive = true
            }
            .doOnDispose {
                println("Dispose of streamUi")
                AdbBusManager.uiHierarchyBusActive = false
            }
}

private val s = "android.support.test.runner.AndroidJUnitRunner"
private val s2 = "com.github.uiautomator"
private val s1 = "$s2.test"

fun AdbDevice.uiAutomatorBridge(): Observable<Boolean> {

    println("chips and beans")

    if (isUiAutomatorActive().not()) {

        println("Trying to start instrumentation")

        command("$SHELL am instrument -w -e package $s2 $s1/$s")
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe { subject.onNext(true) }
                .doOnDispose { subject.onNext(false) }
                .doOnError { println(it) }
                .subscribe()

        println("Passed that no block")

        command("forward tcp:9008 tcp:9008")
                .doOnError { println(it) }
                .subscribe()

        println("Passed that SECOND block")
    }

    println("beans beans")

    return subject.doOnNext {
        println("uiAutomatorBridgeActive = $it")
        AdbBusManager.uiAutomatorBridgeActive = it
    }
}

