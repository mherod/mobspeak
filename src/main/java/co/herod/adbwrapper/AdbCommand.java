package co.herod.adbwrapper;

import co.herod.adbwrapper.model.AdbDevice;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * Created by matthewherod on 23/04/2017.
 */
class AdbCommand {

    static final String ADB = "adb";
    static final String SHELL = "shell";

    private final String deviceIdentifier;
    private final String command;

    AdbCommand(final String deviceIdentifier, final String command) {
        this.deviceIdentifier = deviceIdentifier;
        this.command = command;
    }

    @NotNull
    public ProcessBuilder toProcessBuilder() {

        return new ProcessBuilder()
                .command(createCommandStrings())
                .redirectErrorStream(true);
    }

    @NotNull
    private List<String> createCommandStrings() {
        final String command = getCommand();
        // System.out.println(command);
        return Arrays.asList(command.split(" "));
    }

    @NotNull
    private String getCommand() {
        return ADB + (deviceIdentifier != null ? " -s " + deviceIdentifier : "") + " " + command;
    }

    @SuppressWarnings({"WeakerAccess", "unused"})
    public static class Builder {

        @Nullable
        private String deviceIdentifier;
        private String command;

        @Nullable
        AdbCommand build() {
            return new AdbCommand(deviceIdentifier, command);
        }

        @NotNull
        ProcessBuilder processBuilder() {
            return build().toProcessBuilder();
        }

        @NotNull
        public Builder setDevice(@Nullable final AdbDevice adbDevice) {
            if (adbDevice != null) {
                this.deviceIdentifier = adbDevice.getDeviceIdentifier();
            }
            return this;
        }

        @NotNull
        public Builder setDeviceIdentifier(@Nullable final String deviceIdentifier) {
            this.deviceIdentifier = deviceIdentifier;
            return this;
        }

        @NotNull
        public Builder setCommand(final String command) {
            this.command = command;
            return this;
        }
    }
}
