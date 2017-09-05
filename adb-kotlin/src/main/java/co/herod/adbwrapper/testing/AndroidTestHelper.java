package co.herod.adbwrapper.testing;

import java.util.concurrent.TimeUnit;

/**
 * Created by matthewherod on 05/09/2017.
 */

public interface AndroidTestHelper {

    void assertActivityName(String activityName);

    void assertPower(int minPower);

    void failOnText(String text);

    void failOnText(String text, int timeout, TimeUnit timeUnit);
}
