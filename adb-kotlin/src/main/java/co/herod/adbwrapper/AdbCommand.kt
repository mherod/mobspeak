package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import java.util.*

/**
 * Created by matthewherod on 23/04/2017.
 */
internal class AdbCommand(
        private val deviceIdentifier: String?,
        private val command: String?
) {

    fun toProcessBuilder(): ProcessBuilder = ProcessBuilder()
            .command(createCommandStrings())
            .redirectErrorStream(true)

    private fun createCommandStrings(): List<String> {

        val command = getCommand()

        return Arrays.asList(*command.split(" ".toRegex())
                .dropLastWhile { it.isEmpty() }
                .toTypedArray())
    }

    private fun getCommand(): String =
            ADB + (if (deviceIdentifier != null) " -s " + deviceIdentifier else "") + " " + command

    class Builder {

        private var deviceIdentifier: String? = null
        private var command: String? = null

        private fun build(): AdbCommand? {
            return AdbCommand(deviceIdentifier, command)
        }

        internal fun processBuilder(): ProcessBuilder? {
            return build()?.toProcessBuilder()
        }

        fun setDevice(adbDevice: AdbDevice?): Builder {
            if (adbDevice != null) {
                this.deviceIdentifier = adbDevice.deviceIdentifier
            }
            return this
        }

        fun setDeviceIdentifier(deviceIdentifier: String?): Builder {
            this.deviceIdentifier = deviceIdentifier
            return this
        }

        fun setCommand(command: String): Builder {
            this.command = command
            return this
        }
    }

    companion object {

        val ADB = "adb"
        val SHELL = "shell"
    }
}
