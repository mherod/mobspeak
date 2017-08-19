package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice

internal object AdbProcesses {

    fun devices(): ProcessBuilder = AdbCommand.Builder().setCommand(Adb.DEVICES).processBuilder()

    fun dumpUiHierarchyProcess(adbDevice: AdbDevice?) =
            adb(adbDevice, "exec-out uiautomator dump /dev/tty")

    fun dumpsys(adbDevice: AdbDevice?, type: String) =
            adb(adbDevice, String.format("${AdbCommand.SHELL} dumpsys %s", type))

    fun pressKey(adbDevice: AdbDevice?, key: Int) =
            adb(adbDevice, String.format("${AdbCommand.SHELL} input keyevent %d", key))

    fun inputText(adbDevice: AdbDevice, inputText: String) =
            adb(adbDevice, String.format("${AdbCommand.SHELL} input text %s", inputText))

    fun tap(adbDevice: AdbDevice?, x: Int, y: Int) =
            adb(adbDevice, String.format("${AdbCommand.SHELL} input tap %d %d", x, y))

    fun adb(adbDevice: AdbDevice?, command: String) = AdbCommand.Builder()
            .setDevice(adbDevice)
            .setCommand(command)
            .processBuilder()
}
