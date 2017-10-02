package co.herod.adbwrapper.processes

import org.junit.Assert
import org.junit.Test

class ParseAdbStringKtTest {

    @Test
    fun parseAdbString() {

        val deviceIdentifier = "emulator-5554"
        val deviceType = "device"

        val adbDevice = parseAdbString("$deviceIdentifier\t$deviceType")

        Assert.assertNotNull(adbDevice)
        Assert.assertEquals(deviceIdentifier, adbDevice.deviceIdentifier)
        Assert.assertEquals(deviceType, adbDevice.type)
    }
}