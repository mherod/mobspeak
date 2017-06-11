package co.herod.adbwrapper.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class FixedDurationTransformer implements ObservableTransformer<String, String> {

    private final int timeout;
    private final TimeUnit timeUnit;

    public FixedDurationTransformer(int timeout, TimeUnit timeUnit) {

        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }

    @Override
    public Observable<String> apply(@NotNull Observable<String> upstream) {
        return upstream.retry().timeout(timeout, timeUnit);
    }
}
