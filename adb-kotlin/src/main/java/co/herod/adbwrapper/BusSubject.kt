package co.herod.adbwrapper

import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject


abstract class BusSubject<T> : Subject<T>(), Observer<T> {

    private val processSubject = PublishSubject.create<T>()

    public override fun subscribeActual(observer: Observer<in T>) {
        processSubject.subscribeActual(observer)
    }

    override fun hasObservers(): Boolean {
        return processSubject.hasObservers()
    }

    override fun hasThrowable(): Boolean {
        return processSubject.hasThrowable()
    }

    override fun hasComplete(): Boolean {
        return processSubject.hasComplete()
    }

    override fun getThrowable(): Throwable? {
        return processSubject.throwable
    }

    override fun onSubscribe(d: Disposable) {
        processSubject.onSubscribe(d)
    }

    override fun onNext(s: T) {
        processSubject.onNext(s)
    }

    override fun onError(e: Throwable) {
        processSubject.onError(e)
    }

    override fun onComplete() {
        // ignored
    }
}
