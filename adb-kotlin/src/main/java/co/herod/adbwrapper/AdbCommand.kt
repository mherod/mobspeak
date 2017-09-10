package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import io.reactivex.Observable
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
            ADB + (if (deviceIdentifier != null) " -TEXT_KEY $deviceIdentifier" else "") + " " + command

    class Builder {

        private val processFactory: IProcessFactory = ProcessFactory()

        private var deviceIdentifier: String? = null
        private var command: String? = null

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

        internal fun observable(): Observable<String> {
            return processBuilder()?.toObservable() ?: Observable.error(IllegalStateException())
        }

        private fun build(): AdbCommand? {
            return AdbCommand(deviceIdentifier, command)
        }

        private fun processBuilder(): ProcessBuilder? {
            return build()?.toProcessBuilder()
        }

        private fun ProcessBuilder.toObservable(): Observable<String> {
            return processFactory.observableProcess(this)
        }
    }

    companion object {

        val ADB = "adb"
        val SHELL = "shell"
    }
}
