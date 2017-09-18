package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.annotations.CheckReturnValue

interface AdbOps {
    fun devices(): Observable<String>
    fun adb(adbDevice: AdbDevice, command: String): Observable<String>
    fun readDeviceFile(adbDevice: AdbDevice, filePath: String): Observable<String>
    fun uiautomatorDumpExecOut(adbDevice: AdbDevice): Observable<String>
    fun uiautomatorDump(adbDevice: AdbDevice): Observable<String>
    fun pressKey(adbDevice: AdbDevice, key: Int): Single<String>?
    fun tap(adbDevice: AdbDevice, x: Int, y: Int): Observable<String>
    fun swipe(adbDevice: AdbDevice, x: Int, y: Int, x2: Int, y2: Int, speed: Int = 1000): Single<String>
    fun inputText(adbDevice: AdbDevice, inputText: String): Single<String>
    fun inputKeyEvent(key: Int): String
    fun inputTap(x: Int, y: Int): String
    fun inputSwipe(x: Int, y: Int, x2: Int, y2: Int, speed: Int = 500): String
    fun inputText(inputText: String): String
    fun dumpsys(adbDevice: AdbDevice, type: String): Observable<String>
    fun dumpsys(type: String): String
    fun dumpsys(type: String, pipe: String): String
    fun dumpsys(adbDevice: AdbDevice, type: String, pipe: String): Observable<String>
    fun launchUrl(adbDevice: AdbDevice, url: String): Observable<String>
    fun launchUrl(adbDevice: AdbDevice, url: String, packageName: String): Observable<String>
    fun intentViewUrl(url: String): String
    fun intentViewUrl(url: String, packageName: String): String
    fun uiautomatorDumpAndRead(adbDevice: AdbDevice): Observable<String>
}

internal object AdbProcesses : AdbOps {

    @CheckReturnValue
    override fun devices(): Observable<String> = AdbCommand.Builder()
            .setCommand(S.DEVICES)
            .observable()

    @CheckReturnValue
    override fun uiautomatorDumpAndRead(adbDevice: AdbDevice): Observable<String> =
            adb(adbDevice, "shell \"" +
                    "uiautomator dump ${S.UI_DUMP_XML_PATH}; " +
                    "cat ${S.UI_DUMP_XML_PATH}; " +
                    "echo" +
                    "\"")

    @CheckReturnValue
    override fun uiautomatorDumpExecOut(adbDevice: AdbDevice): Observable<String> =
            adb(adbDevice, "exec-out uiautomator dump /dev/tty")

    @CheckReturnValue
    override fun uiautomatorDump(adbDevice: AdbDevice): Observable<String> =
            adb(adbDevice, "shell uiautomator dump ${S.UI_DUMP_XML_PATH}")

    @CheckReturnValue
    override fun readDeviceFile(adbDevice: AdbDevice, filePath: String): Observable<String> =
            adb(adbDevice, "shell cat $filePath")

    @CheckReturnValue
    override fun dumpsys(adbDevice: AdbDevice, type: String): Observable<String> =
            adb(adbDevice, dumpsys(type))

    @CheckReturnValue
    override fun dumpsys(adbDevice: AdbDevice, type: String, pipe: String): Observable<String> =
            adb(adbDevice, dumpsys(type, pipe))

    @CheckReturnValue
    override fun pressKey(adbDevice: AdbDevice, key: Int): Single<String> =
            adb(adbDevice, inputKeyEvent(key)).lastOrError()

    @CheckReturnValue
    override fun inputText(adbDevice: AdbDevice, inputText: String): Single<String> =
            adb(adbDevice, inputText(inputText)).lastOrError()

    @CheckReturnValue
    override fun tap(adbDevice: AdbDevice, x: Int, y: Int): Observable<String> =
            adb(adbDevice, inputTap(x, y))

    @CheckReturnValue
    override fun swipe(adbDevice: AdbDevice, x: Int, y: Int, x2: Int, y2: Int, speed: Int): Single<String> =
            adb(adbDevice, inputSwipe(x, y, x2, y2, speed)).lastOrError()

    @CheckReturnValue
    override fun launchUrl(adbDevice: AdbDevice, url: String): Observable<String> =
            adb(adbDevice, intentViewUrl(url))

    @CheckReturnValue
    override fun launchUrl(adbDevice: AdbDevice, url: String, packageName: String): Observable<String> =
            adb(adbDevice, intentViewUrl(url, packageName))

    @CheckReturnValue
    override fun adb(adbDevice: AdbDevice, command: String): Observable<String> = AdbCommand.Builder()
            .setDevice(adbDevice)
            .setCommand(command)
            .observable()

    override fun intentViewUrl(url: String): String =
            "${S.SHELL} am start " +
                    "-a ${S.INTENT_ACTION_VIEW} " +
                    "-d '$url'"

    override fun intentViewUrl(url: String, packageName: String): String =
            "${S.SHELL} am start " +
                    "-a ${S.INTENT_ACTION_VIEW} " +
                    "-d '$url' $packageName"

    override fun dumpsys(type: String): String =
            "${S.SHELL} dumpsys $type"

    override fun dumpsys(type: String, pipe: String): String =
            "${S.SHELL} \"dumpsys $type | $pipe\""

    override fun inputKeyEvent(key: Int): String =
            "${S.SHELL} input keyevent $key"

    override fun inputTap(x: Int, y: Int): String =
            "${S.SHELL} input tap $x $y"

    override fun inputSwipe(x: Int, y: Int, x2: Int, y2: Int, speed: Int) =
            "${S.SHELL} input swipe $x $y $x2 $y2 $speed"

    override fun inputText(inputText: String): String =
            "${S.SHELL} input text $inputText"
}
