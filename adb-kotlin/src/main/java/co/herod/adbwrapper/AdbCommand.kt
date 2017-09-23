package co.herod.adbwrapper

import co.herod.adbwrapper.model.AdbDevice
import io.reactivex.Observable

@Suppress("MemberVisibilityCanPrivate")
class AdbCommand(
        val deviceIdentifier: String?,
        val command: String = ""
) {
//    init {
//        System.out.println(this)
//    }

    private fun buildAdbDeviceSerialCommand() =
            S.ADB + (deviceIdentifier?.let { " -s $it " } ?: " ")

    private fun buildShellCommand(): String =
            buildAdbDeviceSerialCommand() + " " + command

    internal fun createShellCommandStrings(): List<String> =
            buildShellCommand()
                    .split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)".toRegex())
                    .filter { it.isNotEmpty() }
                    .map { it.trim() }

    override fun toString(): String {
        return "AdbCommand(deviceIdentifier=$deviceIdentifier, command=$command)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AdbCommand

        if (deviceIdentifier != other.deviceIdentifier) return false
        if (command != other.command) return false

        return true
    }

    override fun hashCode(): Int {
        var result = deviceIdentifier?.hashCode() ?: 0
        result = 31 * result + command.hashCode()
        return result
    }

    class Builder {

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

        override fun toString(): String {
            return "Builder(deviceIdentifier=$deviceIdentifier, command=$command)"
        }
    }
}

fun AdbCommand.Builder.buildProcess(): Process? = build()?.toProcessBuilder()?.start()

fun AdbCommand.Builder.observable(): Observable<String> = with(build()) {
    return when {
        this?.isShellCommand() == true -> {
            this.let { ProcessFactory.observableShellProcess(it) }
        }
        else -> {
            this?.toProcessBuilder()?.toObservable() ?: Observable.error(IllegalStateException())
        }
    }
}

fun AdbCommand.isShellCommand(): Boolean = command.startsWith("shell ")
fun AdbCommand.shellInternalCommand(): String = command.substring(6).trim { it <= '\"' }

fun AdbCommand.toProcessBuilder(): ProcessBuilder = ProcessBuilder()
        .command(createShellCommandStrings())
        .redirectErrorStream(true)

private fun ProcessBuilder.toObservable(): Observable<String> =
        ProcessFactory.observableProcess(this)
