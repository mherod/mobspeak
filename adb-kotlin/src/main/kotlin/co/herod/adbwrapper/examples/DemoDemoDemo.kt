@file:JvmName("DemoDemoDemo")

package co.herod.adbwrapper.examples;

import co.herod.adbwrapper.AdbDeviceManager
import co.herod.adbwrapper.subscribeUiNodesSource
import co.herod.adbwrapper.tap

fun main(args: Array<String>) {

    val device = AdbDeviceManager.getAllDevices().first()

    println("connected this device $device")

    device.subscribeUiNodesSource().doOnNext { uiNode ->

        println("$uiNode")

        if ("Google" in uiNode.text) {
            device.tap(uiNode)
        }
    }.blockingSubscribe()
}


