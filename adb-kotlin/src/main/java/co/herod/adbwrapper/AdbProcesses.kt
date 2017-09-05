package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import io.reactivex.Observable

interface AdbOps {
    fun devices(): ProcessBuilder?
    fun devicesObservable(): Observable<String>
    fun uiautomatorDumpExecOut(adbDevice: AdbDevice?): ProcessBuilder?
    fun uiautomatorDumpExecOutObservable(adbDevice: AdbDevice?): Observable<String>
    fun uiautomatorDump(adbDevice: AdbDevice?): ProcessBuilder?
    fun uiautomatorDumpObservable(adbDevice: AdbDevice?): Observable<String>
    fun readDeviceFile(adbDevice: AdbDevice?, filePath: String): ProcessBuilder?
    fun readDeviceFileObservable(adbDevice: AdbDevice?, filePath: String): Observable<String>
    fun dumpsys(adbDevice: AdbDevice?, type: String): ProcessBuilder?
    fun pressKey(adbDevice: AdbDevice?, key: Int): ProcessBuilder?
    fun inputText(adbDevice: AdbDevice, inputText: String): ProcessBuilder?
    fun tap(adbDevice: AdbDevice?, x: Int, y: Int): ProcessBuilder?
    fun adb(adbDevice: AdbDevice?, command: String): ProcessBuilder?
    fun dumpsys(type: String): String
    fun inputKeyEvent(key: Int): String
    fun inputTap(x: Int, y: Int): String
    fun inputText(inputText: String): String
}

internal object AdbProcesses : AdbOps {

    override fun devices(): ProcessBuilder? = AdbCommand.Builder()
            .setCommand(Adb.DEVICES)
            .processBuilder()

    override fun devicesObservable(): Observable<String> =
            devices().toObservable()

    override fun uiautomatorDumpExecOut(adbDevice: AdbDevice?): ProcessBuilder? =
            adb(adbDevice, "exec-out uiautomator dump /dev/tty")

    override fun uiautomatorDumpExecOutObservable(adbDevice: AdbDevice?): Observable<String> =
            uiautomatorDumpExecOut(adbDevice).toObservable()

    override fun uiautomatorDump(adbDevice: AdbDevice?): ProcessBuilder? =
            adb(adbDevice, "shell uiautomator dump")

    override fun uiautomatorDumpObservable(adbDevice: AdbDevice?): Observable<String> =
            uiautomatorDump(adbDevice).toObservable()

    override fun readDeviceFile(adbDevice: AdbDevice?, filePath: String): ProcessBuilder? =
            adb(adbDevice, "shell cat $filePath")

    override fun readDeviceFileObservable(adbDevice: AdbDevice?, filePath: String): Observable<String> =
            readDeviceFile(adbDevice, "shell cat $filePath").toObservable()

    override fun dumpsys(adbDevice: AdbDevice?, type: String) = adb(adbDevice, dumpsys(type))

    override fun pressKey(adbDevice: AdbDevice?, key: Int) = adb(adbDevice, inputKeyEvent(key))

    override fun inputText(adbDevice: AdbDevice, inputText: String): ProcessBuilder? = adb(adbDevice, inputText(inputText))

    override fun tap(adbDevice: AdbDevice?, x: Int, y: Int): ProcessBuilder? = adb(adbDevice, inputTap(x, y))

    override fun adb(adbDevice: AdbDevice?, command: String): ProcessBuilder? = AdbCommand.Builder()
            .setDevice(adbDevice)
            .setCommand(command)
            .processBuilder()

    override fun dumpsys(type: String) =
            String.format("${AdbCommand.SHELL} dumpsys %s", type)

    override fun inputKeyEvent(key: Int) =
            String.format("${AdbCommand.SHELL} input keyevent %d", key)

    override fun inputTap(x: Int, y: Int) =
            String.format("${AdbCommand.SHELL} input tap %d %d", x, y)

    override fun inputText(inputText: String) =
            String.format("${AdbCommand.SHELL} input text %s", inputText)

    private fun ProcessBuilder?.toObservable(): Observable<String> {
        return Adb.processFactory.observableProcess(this)
    }
}
