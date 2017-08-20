package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice

internal object AdbProcesses {

    fun devices(): ProcessBuilder? = AdbCommand.Builder().setCommand(Adb.DEVICES).processBuilder()

    fun dumpUiHierarchyProcess(adbDevice: AdbDevice?) = adb(adbDevice, "exec-out uiautomator dump /dev/tty")

    fun dumpsys(adbDevice: AdbDevice?, type: String) = adb(adbDevice, dumpsys(type))

    fun pressKey(adbDevice: AdbDevice?, key: Int) = adb(adbDevice, inputKeyEvent(key))

    fun inputText(adbDevice: AdbDevice, inputText: String) = adb(adbDevice, inputText(inputText))

    fun tap(adbDevice: AdbDevice?, x: Int, y: Int) = adb(adbDevice, inputTap(x, y))

    fun adb(adbDevice: AdbDevice?, command: String) = AdbCommand.Builder()
            .setDevice(adbDevice)
            .setCommand(command)
            .processBuilder()

    private fun dumpsys(type: String) =
            String.format("${AdbCommand.SHELL} dumpsys %s", type)

    private fun inputKeyEvent(key: Int) =
            String.format("${AdbCommand.SHELL} input keyevent %d", key)

    private fun inputTap(x: Int, y: Int) =
            String.format("${AdbCommand.SHELL} input tap %d %d", x, y)

    private fun inputText(inputText: String) =
            String.format("${AdbCommand.SHELL} input text %s", inputText)
}
