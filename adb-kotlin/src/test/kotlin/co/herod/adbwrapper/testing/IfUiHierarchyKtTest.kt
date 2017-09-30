//package co.herod.adbwrapper.testing
//
//import co.herod.adbwrapper.AdbDeviceManager
//import org.junit.Assert
//import org.junit.Test
//
//class IfUiHierarchyKtTest {
//
//    @Test(timeout = 5000)
//    fun ifUiHierarchyWhenAllFalse() {
//        AdbDeviceManager.getDevice()?.testHelper().run {
//            val result = this?.ifUiHierarchy {
//                it.childUiNodes.size > 5 && it.childUiNodes.any {
//                    it.uiClass.startsWith("rubbishOS.1.")
//                }
//            }
//            result?.let { Assert.assertFalse(it) }
//        }
//    }
//
//    @Test(timeout = 5000)
//    fun ifUiHierarchyWhenTrue() {
//        AdbDeviceManager.getDevice()?.testHelper().run {
//            val ifUiHierarchy = this?.ifUiHierarchy {
//                it.childUiNodes.size > 5 && it.childUiNodes.any {
//                    it.uiClass.startsWith("android.widget.")
//                }
//            }
//            ifUiHierarchy?.let { Assert.assertTrue(it) }
//        }
//    }
//}