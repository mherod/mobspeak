/*
 * Copyright (c) 2018. Herod
 */

package co.herod.adbwrapper

import co.herod.adbwrapper.S.Companion.DUMPSYS
import co.herod.adbwrapper.S.Companion.INTENT_ACTION_VIEW
import co.herod.adbwrapper.S.Companion.SHELL
import co.herod.adbwrapper.S.Companion.UI_DUMP_XML_PATH
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.InputType
import io.reactivex.Observable
import io.reactivex.annotations.CheckReturnValue

@CheckReturnValue
fun uiautomatorDumpFull(adbDevice: AdbDevice): Observable<String> =
        AdbCommand.Builder()
                .setDevice(adbDevice)
                .setCommand(cmdStringUiautomatorDumpFull())
                .observable()

@CheckReturnValue
fun uiautomatorDumpExecOut(adbDevice: AdbDevice): Observable<String> =
        AdbCommand.Builder()
                .setDevice(adbDevice)
                .setCommand("exec-out uiautomator dump /dev/tty")
                .observable()

@CheckReturnValue
fun uiautomatorDump(adbDevice: AdbDevice): Observable<String> =
        AdbCommand.Builder()
                .setDevice(adbDevice)
                .setCommand("shell uiautomator dump $UI_DUMP_XML_PATH")
                .observable()

@CheckReturnValue
fun readDeviceFile(adbDevice: AdbDevice, filePath: String): Observable<String> =
        AdbCommand.Builder()
                .setDevice(adbDevice)
                .setCommand(cmdStringCatFile(filePath))
                .observable()

@CheckReturnValue
fun AdbDevice.dumpsys(type: String): Observable<String> =
        AdbCommand.Builder()
                .setDevice(this)
                .setCommand(cmdStringDumpsys(type))
                .observable()

@CheckReturnValue
fun AdbDevice.dumpsys(type: String, pipe: String): Observable<String> =
        AdbCommand.Builder()
                .setDevice(this)
                .setCommand(cmdStringDumpsys(type, pipe))
                .observable()

@CheckReturnValue
fun AdbDevice.pressKey(key: CharSequence): Observable<String> =
        AdbCommand.Builder()
                .setDevice(this)
                .setCommand(cmdStringInputKey(key))
                .observable()

@CheckReturnValue
fun launchUrl(adbDevice: AdbDevice, url: String): Observable<String> =
        AdbCommand.Builder()
                .setDevice(adbDevice)
                .setCommand(cmdStringIntentViewUrl(url))
                .observable()

@CheckReturnValue
fun launchUrl(adbDevice: AdbDevice, url: String, packageName: String): Observable<String> =
        AdbCommand.Builder()
                .setDevice(adbDevice)
                .setCommand(cmdStringIntentViewUrl(url, packageName))
                .observable()

@CheckReturnValue
fun cmdStringInputKey(key: CharSequence): String =
        "$SHELL input ${InputType.KEY_EVENT} $key"

@JvmOverloads
@CheckReturnValue
fun cmdStringIntentViewUrl(url: String, packageName: String = ""): String =
        "$SHELL am start -a $INTENT_ACTION_VIEW ${"-d '$url' $packageName".trim()}"

@CheckReturnValue
private fun cmdStringDumpsys(type: String): String =
        "$SHELL $DUMPSYS $type"

@CheckReturnValue
private fun cmdStringDumpsys(type: String, pipe: String): String =
        "$SHELL \"$DUMPSYS $type | $pipe\""

@CheckReturnValue
private fun cmdStringUiautomatorDumpFull(): String =
        "$SHELL \"uiautomator dump $UI_DUMP_XML_PATH; cat $UI_DUMP_XML_PATH; echo\""

@CheckReturnValue
private fun cmdStringCatFile(filePath: String): String =
        "$SHELL cat $filePath"
