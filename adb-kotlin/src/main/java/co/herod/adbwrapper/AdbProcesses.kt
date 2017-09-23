package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import io.reactivex.Observable
import io.reactivex.annotations.CheckReturnValue

@CheckReturnValue
fun devices(): Observable<String> = AdbCommand.Builder()
        .setCommand(S.DEVICES)
        .observable()

@CheckReturnValue
fun uiautomatorDumpAndRead(adbDevice: AdbDevice): Observable<String> =
        AdbCommand.Builder()
                .setDevice(adbDevice)
                .setCommand("shell \"" +
                        "uiautomator dump ${S.UI_DUMP_XML_PATH}; " +
                        "cat ${S.UI_DUMP_XML_PATH}; " +
                        "echo" +
                        "\"")
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
                .setCommand("shell uiautomator dump ${S.UI_DUMP_XML_PATH}")
                .observable()

@CheckReturnValue
fun readDeviceFile(adbDevice: AdbDevice, filePath: String): Observable<String> =
        AdbCommand.Builder()
                .setDevice(adbDevice)
                .setCommand("shell cat $filePath")
                .observable()

@CheckReturnValue
fun dumpsys(adbDevice: AdbDevice, type: String): Observable<String> =
        AdbCommand.Builder()
                .setDevice(adbDevice)
                .setCommand(dumpsys(type))
                .observable()

@CheckReturnValue
fun dumpsys(adbDevice: AdbDevice, type: String, pipe: String): Observable<String> =
        AdbCommand.Builder()
                .setDevice(adbDevice)
                .setCommand(dumpsys(type, pipe))
                .observable()

@CheckReturnValue
fun pressKey(adbDevice: AdbDevice, key: CharSequence): Observable<String> =
        AdbCommand.Builder()
                .setDevice(adbDevice)
                .setCommand("${S.SHELL} input keyevent $key")
                .observable()

@CheckReturnValue
fun launchUrl(adbDevice: AdbDevice, url: String): Observable<String> =
        AdbCommand.Builder()
                .setDevice(adbDevice)
                .setCommand(intentViewUrl(url))
                .observable()

@CheckReturnValue
fun launchUrl(adbDevice: AdbDevice, url: String, packageName: String): Observable<String> =
        AdbCommand.Builder()
                .setDevice(adbDevice)
                .setCommand(intentViewUrl(url, packageName))
                .observable()

@JvmOverloads
fun intentViewUrl(url: String, packageName: String = ""): String =
        "${S.SHELL} am start -a ${S.INTENT_ACTION_VIEW} ${"-d '$url' $packageName".trim()}"

fun dumpsys(type: String): String =
        "${S.SHELL} dumpsys $type"

fun dumpsys(type: String, pipe: String): String =
        "${S.SHELL} \"dumpsys $type | $pipe\""
