package co.herod.adbwrapper;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by matthewherod on 23/04/2017.
 */
class AdbBus extends Subject<String> implements Observer<String> {

    private static AdbBus ourInstance = new AdbBus();

    private final PublishSubject<String> processOutputSubject = PublishSubject.create();

    private AdbBus() {
    }

    static AdbBus getBus() {
        return ourInstance;
    }

    @Override
    protected void subscribeActual(Observer<? super String> observer) {
        processOutputSubject.subscribeActual(observer);
    }

    @Override
    public boolean hasObservers() {
        return processOutputSubject.hasObservers();
    }

    @Override
    public boolean hasThrowable() {
        return processOutputSubject.hasThrowable();
    }

    @Override
    public boolean hasComplete() {
        return processOutputSubject.hasComplete();
    }

    @Override
    public Throwable getThrowable() {
        return processOutputSubject.getThrowable();
    }

    @Override
    public void onSubscribe(Disposable d) {
        processOutputSubject.onSubscribe(d);
    }

    @Override
    public void onNext(String s) {
        processOutputSubject.onNext(s);
    }

    @Override
    public void onError(Throwable e) {
        processOutputSubject.onError(e);
    }

    @Override
    public void onComplete() {
        // ignored
    }
}
