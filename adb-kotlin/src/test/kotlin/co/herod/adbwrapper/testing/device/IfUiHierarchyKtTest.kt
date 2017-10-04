package co.herod.adbwrapper.testing.device

import co.herod.adbwrapper.AdbDeviceManager
import co.herod.adbwrapper.testing.ifUiHierarchy
import co.herod.adbwrapper.testing.testHelper
import org.junit.Assert
import org.junit.Test

class IfUiHierarchyKtTest {

    @Test(timeout = 10 * 1000)
    fun ifUiHierarchyWhenAllFalse() {
        AdbDeviceManager.getDevice()?.testHelper().run {
            val result = this?.ifUiHierarchy {
                it.uiNodes.size > 5 && it.uiNodes.any {
                    it.uiClass.startsWith("rubbishOS.1.")
                }
            }
            result?.let { Assert.assertFalse(it) }
        }
    }

    @Test(timeout = 10 * 1000)
    fun ifUiHierarchyWhenTrue() {
        AdbDeviceManager.getDevice()?.testHelper().run {
            val ifUiHierarchy = this?.ifUiHierarchy {
                it.uiNodes.size > 5 && it.uiNodes.any {
                    it.uiClass.startsWith("android.widget.")
                }
            }
            ifUiHierarchy?.let { Assert.assertTrue(it) }
        }
    }
}