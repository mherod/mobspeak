@file:JvmName("DemoDemoDemo")

package co.herod.adbwrapper;

fun main(args: Array<String>) {

    val device = AdbDeviceManager.getAllDevices().first()

    println("connected this device $device")

    device.subscribeUiNodes().doOnNext { uiNode ->

        println("$uiNode")

        if ("View" in uiNode.text) {
            device.touchUiNode(uiNode)
        }
    }.blockingSubscribe()
}


