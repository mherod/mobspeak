package co.herod.adbwrapper;

/**
 * Created by matthewherod on 04/09/2017.
 */

public class AdbError extends RuntimeException {

    private final String errorMessage;

    public AdbError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {

        return "AdbError{" +
                "errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
