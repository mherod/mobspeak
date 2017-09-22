package co.herod.kotlin.ext

import co.herod.adbwrapper.model.DumpsysEntry
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit

fun <T> Observable<T>.timeout(
        timeout: Int,
        timeUnit: TimeUnit
): Observable<T> =
        timeout(timeout.toLong(), timeUnit)

fun <T> Observable<T>.retryWithTimeout(
        timeout: Int,
        timeUnit: TimeUnit
): Observable<T> =
        retry().timeout(timeout.toLong(), timeUnit)

//fun <T> Single<T>.retryWithTimeout(
//        timeout: Int,
//        timeUnit: TimeUnit
//): Single<T> =
//        retry().timeout(timeout.toLong(), timeUnit)

fun Observable<String>.blocking(
        timeout: Int = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): String = this
        .retryWithTimeout(timeout, timeUnit)
        .last("")
        .onErrorReturn { "" }
        .blockingGet()

fun Observable<String>.blockingSingle(
        timeout: Int = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): String = this
        .retry()
        .firstOrError()
        .timeout(timeout.toLong(), timeUnit)
        .onErrorReturn { "" }
        .blockingGet()

fun Single<String>.blockingSingle(
        timeout: Int = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): String = this
        .timeout(timeout.toLong(), timeUnit)
        .onErrorReturn { "" }
        .blockingGet()

@JvmName("filterPropertiesByKey")
fun Observable<DumpsysEntry>.filterKeys(vararg keys: String): Observable<Map<String, String>> =
        toMapRxSingle().map { it.filterKeys { it in keys } }.toObservable()

fun Observable<Map<String, String>>.filterKeys(vararg keys: String): Observable<Map<String, String>> =
        map { it.filterKeys { it in keys } }

fun Single<Map<String, String>>.filterKeys(vararg keys: String): Single<Map<String, String>> =
        map { it.filterKeys { it in keys } }

fun Single<Map<String, String>>.observableValues(): Observable<String> =
        toObservable().flatMapIterable { it.values }

fun Observable<Map<String, String>>.observableValues(): Observable<String> =
        flatMapIterable { it.values }

fun Observable<Map<String, String>>.valueOf(key: String): Observable<String> =
        map { it[key] }

fun Observable<DumpsysEntry>.toMapRx(): Observable<MutableMap<String, String>> =
        toMap({ it -> it.key }, { it -> it.value }).toObservable()

fun Observable<DumpsysEntry>.toMapRxSingle(): Single<MutableMap<String, String>> =
        toMap({ it -> it.key }, { it -> it.value })
