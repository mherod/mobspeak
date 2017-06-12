package co.herod.adbwrapper;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import org.jetbrains.annotations.NotNull;

public abstract class BusSubject<T> extends Subject<T> implements Observer<T> {

    private final PublishSubject<T> processSubject = PublishSubject.create();

    @Override
    protected void subscribeActual(@NotNull final Observer<? super T> observer) {
        processSubject.subscribeActual(observer);
    }

    @Override
    public boolean hasObservers() {
        return processSubject.hasObservers();
    }

    @Override
    public boolean hasThrowable() {
        return processSubject.hasThrowable();
    }

    @Override
    public boolean hasComplete() {
        return processSubject.hasComplete();
    }

    @Override
    public Throwable getThrowable() {
        return processSubject.getThrowable();
    }

    @Override
    public void onSubscribe(final Disposable d) {
        processSubject.onSubscribe(d);
    }

    @Override
    public void onNext(final T s) {
        processSubject.onNext(s);
    }

    @Override
    public void onError(final Throwable e) {
        processSubject.onError(e);
    }

    @Override
    public void onComplete() {
        // ignored
    }
}
