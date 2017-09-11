package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import io.reactivex.Observable
import io.reactivex.annotations.CheckReturnValue

interface AdbOps {
    fun devices(): Observable<String>
    fun adb(adbDevice: AdbDevice, command: String): Observable<String>
    fun readDeviceFile(adbDevice: AdbDevice, filePath: String): Observable<String>
    fun uiautomatorDumpExecOut(adbDevice: AdbDevice): Observable<String>
    fun uiautomatorDumpExecOutObservable(adbDevice: AdbDevice): Observable<String>
    fun uiautomatorDump(adbDevice: AdbDevice): Observable<String>
    fun pressKey(adbDevice: AdbDevice, key: Int): Observable<String>
    fun tap(adbDevice: AdbDevice, x: Int, y: Int): Observable<String>
    fun swipe(adbDevice: AdbDevice, x: Int, y: Int, x2: Int, y2: Int, speed: Int = 1000): Observable<String>
    fun inputText(adbDevice: AdbDevice, inputText: String): Observable<String>
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
}

internal object AdbProcesses : AdbOps {

    private val intentActionView = "android.intent.action.VIEW"

    @CheckReturnValue
    override fun devices(): Observable<String> = AdbCommand.Builder()
            .setCommand(Adb.DEVICES)
            .observable()

    @CheckReturnValue
    override fun uiautomatorDumpExecOut(adbDevice: AdbDevice): Observable<String> =
            adb(adbDevice, "exec-out uiautomator dump /dev/tty")

    @CheckReturnValue
    override fun uiautomatorDumpExecOutObservable(adbDevice: AdbDevice): Observable<String> =
            uiautomatorDumpExecOut(adbDevice)

    @CheckReturnValue
    override fun uiautomatorDump(adbDevice: AdbDevice): Observable<String> =
            adb(adbDevice, "shell uiautomator dump")

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
    override fun pressKey(adbDevice: AdbDevice, key: Int): Observable<String> =
            adb(adbDevice, inputKeyEvent(key))

    @CheckReturnValue
    override fun inputText(adbDevice: AdbDevice, inputText: String): Observable<String> =
            adb(adbDevice, inputText(inputText))

    @CheckReturnValue
    override fun tap(adbDevice: AdbDevice, x: Int, y: Int): Observable<String> =
            adb(adbDevice, inputTap(x, y))

    @CheckReturnValue
    override fun swipe(adbDevice: AdbDevice, x: Int, y: Int, x2: Int, y2: Int, speed: Int): Observable<String> =
            adb(adbDevice, inputSwipe(x, y, x2, y2, speed))

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
            "${AdbCommand.SHELL}  am start -a $intentActionView -d '$url'"

    override fun intentViewUrl(url: String, packageName: String): String =
            "${AdbCommand.SHELL}  am start -a $intentActionView -d '$url' $packageName"

    override fun dumpsys(type: String): String =
            "${AdbCommand.SHELL} dumpsys $type"

    override fun dumpsys(type: String, pipe: String): String =
            "${AdbCommand.SHELL} \"dumpsys $type | $pipe\""

    override fun inputKeyEvent(key: Int): String =
            "${AdbCommand.SHELL} input keyevent $key"

    override fun inputTap(x: Int, y: Int): String =
            "${AdbCommand.SHELL} input tap $x $y"

    override fun inputSwipe(x: Int, y: Int, x2: Int, y2: Int, speed: Int) =
            "${AdbCommand.SHELL} input swipe $x $y $x2 $y2 $speed"

    override fun inputText(inputText: String): String =
            "${AdbCommand.SHELL} input text $inputText"
}
