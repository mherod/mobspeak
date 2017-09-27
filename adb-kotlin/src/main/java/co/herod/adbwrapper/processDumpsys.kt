package co.herod.adbwrapper

import io.reactivex.Observable
import io.reactivex.Single

fun Observable<String>.processDumpsys(c: String): Single<Map<String, String>> =
        this
                // .doOnNext(System.out::println)
                .filter { c in it }
                .map { it.trim() }
                .map {
                    it.split(
                            regex = c.toRegex(),
                            limit = 2
                    )
                }
                .toMap({ it[0].trim() }) { it[1].trim() }