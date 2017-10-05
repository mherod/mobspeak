@file:Suppress("unused", "MemberVisibilityCanPrivate")

package co.herod.adbwrapper.bus

import co.herod.adbwrapper.model.UiHierarchy
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

class UiHierarchyBus :
        Subject<UiHierarchy>(),
        Observer<UiHierarchy>,
        Disposable,
        DisposableContainer {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    @Suppress("PropertyName")
    private val uiHierarchySubject: BehaviorSubject<UiHierarchy> =
            BehaviorSubject.create<UiHierarchy>()

    val uiHierarchyBus: Observable<UiHierarchy> by lazy {
        uiHierarchySubject
                .doOnSubscribe { }
                .doOnDispose { }
    }

    override fun subscribeActual(observer: Observer<in UiHierarchy>) {
        uiHierarchyBus.subscribe(observer)
    }

    override fun onComplete() {
//        uiHierarchySubject.onComplete()
    }

    override fun hasThrowable(): Boolean = uiHierarchySubject.hasThrowable()

    override fun hasComplete(): Boolean = uiHierarchySubject.hasComplete()

    override fun onNext(t: UiHierarchy) = uiHierarchySubject.onNext(t)

    override fun onError(e: Throwable) = uiHierarchySubject.onError(e)

    override fun onSubscribe(d: Disposable) {
        uiHierarchySubject.onSubscribe(d)
    }

    override fun hasObservers(): Boolean = uiHierarchySubject.hasObservers()

    override fun getThrowable(): Throwable? = uiHierarchySubject.throwable

    override fun isDisposed(): Boolean = compositeDisposable.isDisposed

    override fun dispose() = compositeDisposable.dispose()

    override fun add(d: Disposable?): Boolean =
            d?.let { compositeDisposable.add(it) } ?: false

    override fun remove(d: Disposable?): Boolean =
            d?.let { compositeDisposable.remove(it) } ?: false

    override fun delete(d: Disposable?): Boolean =
            d?.let { compositeDisposable.delete(it) } ?: false
}