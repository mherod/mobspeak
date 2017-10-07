@file:Suppress("unused")

package co.herod.kotlin.ext

import co.herod.adbwrapper.model.DumpsysEntry
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import kotlin.system.measureTimeMillis

fun <T> Observable<T>.timeout(
        timeout: Int = 5,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): Observable<T> = timeout(timeout.toLong(), timeUnit)

fun <T> Single<T>.timeout(
        timeout: Int = 5,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): Single<T> = timeout(timeout.toLong(), timeUnit)

fun <T> Observable<T>.retryWithTimeout(
        timeout: Int = 5,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): Observable<T> = this
        .retry()
        .timeout(timeout, timeUnit)

fun <T> Single<T>.retryWithTimeout(
        timeout: Int = 5,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): Single<T> = this
//        .retry()
        .timeout(timeout, timeUnit)

//fun Observable<String>.toSingleBlocking(
//        timeout: Int = 5,
//        timeUnit: TimeUnit = TimeUnit.SECONDS
//) = try {
//    this
//            // .lastOrError()
//            // .retryWithTimeout(timeout, timeUnit)
//            .timeout(timeout.toLong(), timeUnit)
//            // .doOnSuccess { System.out.println(it) }
//            // .blockingGet()
//            .blockingSubscribe()
//} catch (exception: IllegalStateException) {
//    throw AssertionError(exception)
//}

fun Observable<String>.blockingSilent(
        timeout: Int = 5,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): String = this
        .last("") // succeeds silently
        .timeout(timeout.toLong(), timeUnit)
        .blockingGet()

fun Single<String>.blocking(
        timeout: Int = 5,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): String = this
        .timeout(timeout.toLong(), timeUnit)
        .onErrorResumeNext { throwable ->
            if (throwable is TimeoutException) {
                Single.error<String> {
                    AssertionError("Timed out")
                }
            }
            Single.error<String> { throwable }
        }
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

fun CompositeDisposable.waitUntilEmpty(): Long =
        measureTimeMillis {
            Observable.fromCallable { size() }
                    .repeat()
                    .distinctUntilChanged()
                    .takeUntil { it == 0 }
                    .flatMap { println(it); Observable.timer(100, TimeUnit.MILLISECONDS) }
                    .blockingSubscribe()
        }