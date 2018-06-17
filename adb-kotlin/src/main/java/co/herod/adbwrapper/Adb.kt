/*
 * Copyright (c) 2018. Herod
 */

@file:Suppress("unused")

package co.herod.adbwrapper

import co.herod.adbwrapper.S.Companion.PROPERTY_CURRENT_FOCUS
import co.herod.adbwrapper.S.Companion.PROPERTY_FOCUSED_APP
import co.herod.adbwrapper.device.dump
import co.herod.adbwrapper.device.dumpsys
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.DumpsysKey
import co.herod.adbwrapper.props.processDumpsys
import co.herod.kotlin.ext.filterKeys
import io.reactivex.Observable

private fun AdbDevice.dumpsysMap(type: String, pipe: String): Observable<Map<String, String>>? =
        dumpsys(type, pipe)
                .processDumpsys("=")
                .toObservable()

fun AdbDevice.getActivityDumpsys(): Observable<Map<String, String>> =
        dumpsys("activity")
                .processDumpsys("=")
                .toObservable()

fun AdbDevice.getActivitiesDumpsys(): Observable<Map<String, String>> =
        dumpsys("activity activities")
                .processDumpsys("=")
                .toObservable()

fun AdbDevice.getWindowFocusDumpsys() = dumpsys()
        .dump(DumpsysKey.WINDOW, "windows")
        .filterKeys(PROPERTY_CURRENT_FOCUS, PROPERTY_FOCUSED_APP)

