package co.herod.adbwrapper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * Created by matthewherod on 23/04/2017.
 */
class AdbCommand {

    private static final String ADB = "adb";

    private final String deviceIdentifier;
    private final String command;

    AdbCommand(String deviceIdentifier, String command) {
        this.deviceIdentifier = deviceIdentifier;
        this.command = command;
    }

    public ProcessBuilder toProcessBuilder() {
        return new ProcessBuilder()
                .command(createCommandStrings())
                .redirectErrorStream(true);
    }

    @NotNull
    private List<String> createCommandStrings() {
        final String command = getCommand();
        System.out.println(command);
        return Arrays.asList(command.split(" "));
    }

    @NotNull
    private String getCommand() {
        return ADB + (deviceIdentifier != null ? " -s " + deviceIdentifier : "") + " " + command;
    }

    public static class Builder {

        private String deviceIdentifier;
        private String command;

        AdbCommand build() {
            return new AdbCommand(deviceIdentifier, command);
        }

        ProcessBuilder processBuilder() {
            return build().toProcessBuilder();
        }

        public Builder setDevice(@Nullable final Device device) {
            if (device != null) {
                this.deviceIdentifier = device.deviceIdentifier;
            }
            return this;
        }

        public Builder setDeviceIdentifier(@Nullable final String deviceIdentifier) {
            this.deviceIdentifier = deviceIdentifier;
            return this;
        }

        public Builder setCommand(String command) {
            this.command = command;
            return this;
        }
    }
}
