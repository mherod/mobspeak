/*
 * Copyright (c) 2018. Herod
 */

@file:Suppress("unused")

package co.herod.adbwrapper.model

import co.herod.adbwrapper.util.hasPositiveValue
import io.reactivex.Observable
import io.reactivex.Single
import kotlin.collections.Map.Entry

class DumpsysEntry(private val entry: Entry<String, String>): Entry<String, String> {
    override val key: String
        get() = entry.key
    override val value: String
        get() = entry.value
}

fun Single<DumpsysEntry>.hasPositiveValue(): Boolean = blockingGet().hasPositiveValue()

fun Observable<DumpsysEntry>.completeMap(): Single<MutableMap<String, DumpsysEntry>> =
        toMap { it.key }

fun Observable<DumpsysEntry>.property(key: String): DumpsysEntry? =
        completeMap().map { it[key] }.blockingGet()

fun Observable<DumpsysEntry>.isPropertyPositive(key: String): Boolean =
        filterProperty(key).blockingGet().hasPositiveValue()

fun Observable<DumpsysEntry>.filterProperty(s: String): Single<DumpsysEntry> =
        filter { entry -> entry.key == s }.firstOrError()