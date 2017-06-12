package co.herod.adbwrapper.rx;

import co.herod.adbwrapper.util.StringUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

public class MuteErrorTransformer<T> implements ObservableTransformer<T, String> {

    @Override
    public ObservableSource<String> apply(final Observable<T> upstream) {
        return upstream.map(blah -> "").onErrorReturn(a -> "").filter(StringUtils::isNotEmpty);
    }
}
