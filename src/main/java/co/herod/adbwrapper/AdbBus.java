package co.herod.adbwrapper;

import co.herod.adbwrapper.util.Utils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import org.jetbrains.annotations.NotNull;

/**
 * Created by matthewherod on 23/04/2017.
 */
public class AdbBus extends Subject<String> implements Observer<String> {

    @NotNull
    private static AdbBus ourInstance = new AdbBus();

    private final PublishSubject<String> processOutputSubject = PublishSubject.create();

    private AdbBus() {
    }

    @NotNull
    static AdbBus getBus() {
        return ourInstance;
    }

    static Observable<String> getThrottledBus() {
        return getBus().concatMap(Utils::throttleOutput);
    }

    @Override
    protected void subscribeActual(@NotNull final Observer<? super String> observer) {
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
    public void onSubscribe(final Disposable d) {
        processOutputSubject.onSubscribe(d);
    }

    @Override
    public void onNext(final String s) {
        processOutputSubject.onNext(s);
    }

    @Override
    public void onError(final Throwable e) {
        processOutputSubject.onError(e);
    }

    @Override
    public void onComplete() {
        // ignored
    }
}
