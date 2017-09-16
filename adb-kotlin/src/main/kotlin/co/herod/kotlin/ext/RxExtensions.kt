package co.herod.kotlin.ext

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

fun <T> Single<T>.retryWithTimeout(
        timeout: Int,
        timeUnit: TimeUnit
): Single<T> =
        retry().timeout(timeout.toLong(), timeUnit)

fun Observable<String>.blocking(
        timeout: Int = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): String = this
        .retryWithTimeout(timeout, timeUnit)
        .last("")
        .blockingGet()

fun Single<String>.blocking(
        timeout: Int = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): String = this
        .retryWithTimeout(timeout, timeUnit)
        .onErrorReturn { "" }
        .blockingGet()
