package co.herod.adbwrapper.model

class AdbUiHierarchy(val xmlString: String, val adbDevice: AdbDevice?) {
    override fun toString() = "AdbUiHierarchy{xmlString='$xmlString', adbDevice=$adbDevice}"
}
