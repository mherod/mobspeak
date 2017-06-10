package co.herod.adbwrapper.rx;

import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class ResultChangeFixedDurationTransformer extends FixedDurationTransformer {

    public ResultChangeFixedDurationTransformer() {
        super(2, TimeUnit.DAYS);
    }

    @Override
    public Observable<String> apply(Observable<String> upstream) {
        return super.apply(upstream.repeat()).distinctUntilChanged();
    }
}
