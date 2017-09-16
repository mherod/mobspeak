package co.herod.adbwrapper.model

import co.herod.adbwrapper.util.PropHelper
import io.reactivex.Observable
import io.reactivex.Single
import kotlin.collections.Map.Entry

/**
 * Created by matthewherod on 16/09/2017.
 */

class DumpsysEntry(private val entry: Entry<String, String>): Entry<String, String> {
    override val key: String
        get() = entry.key
    override val value: String
        get() = entry.value
}

fun Observable<DumpsysEntry>.property(s: String): Single<DumpsysEntry> =
        filter { entry -> entry.key == s }.firstOrError()

fun Single<DumpsysEntry>.isPositive(): Boolean =
        map(PropHelper::hasPositiveValue).blockingGet()