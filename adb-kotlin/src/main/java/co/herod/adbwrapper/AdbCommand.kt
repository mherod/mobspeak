package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import io.reactivex.Observable

/**
 * Created by matthewherod on 23/04/2017.
 */

@Suppress("MemberVisibilityCanPrivate")
class AdbCommand(
        val deviceIdentifier: String?,
        val command: String = ""
) {
    init {
        System.out.println(toString())
    }

    fun isShellCommand(): Boolean = command.startsWith("shell ")

    fun shellInternalCommand(): String = command.substring(6).trim { it <= '\"' }

    fun toProcessBuilder(): ProcessBuilder = ProcessBuilder()
            .command(createShellCommandStrings())
            .redirectErrorStream(true)

    private fun buildAdbDeviceSerialCommand() =
            ADB + (deviceIdentifier?.let { " -s $it " } ?: " ")

    private fun buildShellCommand(): String =
            buildAdbDeviceSerialCommand() + " " + command

    private fun createShellCommandStrings(): List<String> =
            buildShellCommand()
                    .split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)".toRegex())
                    .filter { it.isNotEmpty() }
                    .map { it.trim() }

    override fun toString(): String {
        return "AdbCommand(deviceIdentifier=$deviceIdentifier, command=$command)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AdbCommand) return false

        if (deviceIdentifier != other.deviceIdentifier) return false
        if (command != other.command) return false

        return true
    }

    override fun hashCode(): Int {
        var result = deviceIdentifier?.hashCode() ?: 0
        result = 31 * result + (command.hashCode() ?: 0)
        return result
    }

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
            this.deviceIdentifier = deviceIdentifier ?: deviceIdentifier
            return this
        }

        fun setCommand(command: String): Builder {
            this.command = command
            return this
        }

        fun build(): AdbCommand? = AdbCommand(deviceIdentifier, command ?: "")

        internal fun observable(): Observable<String> = with(build()) {
            return if (this?.isShellCommand() == true) {
                processFactory.observableShellProcess(this)
            } else {
                this?.toProcessBuilder()?.toObservable()
            } ?: Observable.error(IllegalStateException())
        }

        fun buildProcess(): Process? = build()?.toProcessBuilder()?.start()

        private fun ProcessBuilder.toObservable(): Observable<String> {
            return processFactory.observableProcess(this)
        }

        override fun toString(): String {
            return "Builder(processFactory=$processFactory, deviceIdentifier=$deviceIdentifier, command=$command)"
        }
    }

    companion object {

        val ADB = "adb"
        val SHELL = "shell"
    }
}
