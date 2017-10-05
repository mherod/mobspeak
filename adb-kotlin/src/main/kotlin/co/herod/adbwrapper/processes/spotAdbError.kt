package co.herod.adbwrapper.processes

import co.herod.adbwrapper.exceptions.AdbError
import io.reactivex.Observable

internal fun spotAdbError(it: String): Observable<String> =
        when {
            it.startsWith("Android Debug Bridge version ") -> {
                Observable.error(AdbError("Invalid output: $it"))
            }
            it.startsWith("ERROR: ") -> {
                Observable.error(AdbError(it))
            }
            "/system/bin/sh" in it -> {
                Observable.error(AdbError("Invalid output: $it"))
            }
            "** No activities found to run, monkey aborted" in it -> {
                Observable.error(AdbError(it.trim()))
            }
            else -> Observable.just(it)
        }

internal fun Observable<String>.spotAdbError(): Observable<String> =
        this.flatMap { spotAdbError(it) }