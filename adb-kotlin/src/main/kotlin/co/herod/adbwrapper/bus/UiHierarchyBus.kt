@file:Suppress("unused", "MemberVisibilityCanPrivate")

package co.herod.adbwrapper.bus

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiHierarchy
import co.herod.adbwrapper.ui.subscribeUiHierarchySource
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

class UiHierarchyBus(val adbDevice: AdbDevice) :
        Subject<UiHierarchy>(),
        Observer<UiHierarchy>,
        Disposable,
        DisposableContainer {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    @Suppress("PropertyName")
    private val subject: BehaviorSubject<UiHierarchy> =
            BehaviorSubject.create<UiHierarchy>()

    val observable: Observable<UiHierarchy> by lazy {

        // just once
        adbDevice.subscribeUiHierarchySource().subscribe(this)

        subject
//                .doOnSubscribe { println("subscribe subscribeUiHierarchySource") }
//                .doOnDispose { println("dispose subscribeUiHierarchySource") }
    }

    override fun subscribeActual(observer: Observer<in UiHierarchy>) {
        observable.subscribe(observer)
    }

    override fun onComplete() {
//        uiHierarchySubject.onComplete()
    }

    override fun hasThrowable(): Boolean = subject.hasThrowable()

    override fun hasComplete(): Boolean = subject.hasComplete()

    override fun onNext(t: UiHierarchy) = subject.onNext(t)

    override fun onError(e: Throwable) = subject.onError(e)

    override fun onSubscribe(d: Disposable) {
        if (subject != d) {
            subject.onSubscribe(d)
        }
    }

    override fun hasObservers(): Boolean = subject.hasObservers()

    override fun getThrowable(): Throwable? = subject.throwable

    override fun isDisposed(): Boolean = compositeDisposable.isDisposed

    override fun dispose() = compositeDisposable.dispose()

    override fun add(d: Disposable?): Boolean =
            d?.let { compositeDisposable.add(it) } ?: false

    override fun remove(d: Disposable?): Boolean =
            d?.let { compositeDisposable.remove(it) } ?: false

    override fun delete(d: Disposable?): Boolean =
            d?.let { compositeDisposable.delete(it) } ?: false
}