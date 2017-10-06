package co.herod.adbwrapper.ui

import co.herod.adbwrapper.AdbBusManager
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class Blah { // TODO refactor into UiHierarchyBus
    companion object {
        val subject: BehaviorSubject<Boolean> =
                BehaviorSubject.createDefault<Boolean>(false)

        val sub1: Observable<Boolean> by lazy {
            subject.doOnNext { AdbBusManager.uiAutomatorBridgeActive = it }
        }
    }
}