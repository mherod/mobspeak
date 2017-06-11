package co.herod.adbwrapper.rx;

import io.reactivex.Observable;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class ResultChangeFixedDurationTransformer extends FixedDurationTransformer {

    public ResultChangeFixedDurationTransformer() {
        super(2, TimeUnit.DAYS);
    }

    @Override
    public Observable<String> apply(@NotNull Observable<String> upstream) {
        return super.apply(upstream.repeat()).distinctUntilChanged();
    }
}
