package co.herod.adbwrapper;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;

import java.util.concurrent.TimeUnit;

class FixedDurationTransformer implements ObservableTransformer<String, String> {

    private final int timeout;
    private final TimeUnit timeUnit;

    public FixedDurationTransformer(int timeout, TimeUnit timeUnit) {

        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }

    @Override
    public Observable<String> apply(Observable<String> upstream) {

        return upstream
                .retry()
                .timeout(timeout, timeUnit);
    }
}
