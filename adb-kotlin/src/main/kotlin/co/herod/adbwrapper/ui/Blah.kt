package co.herod.adbwrapper.ui

import io.reactivex.subjects.BehaviorSubject

class Blah {
    companion object {
        val subject: BehaviorSubject<Boolean> =
                BehaviorSubject.createDefault<Boolean>(false)
    }
}