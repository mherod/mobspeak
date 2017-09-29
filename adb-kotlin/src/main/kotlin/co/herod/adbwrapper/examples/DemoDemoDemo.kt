@file:JvmName("DemoDemoDemo")

package co.herod.adbwrapper.examples

import co.herod.adbwrapper.AdbDeviceManager
import co.herod.adbwrapper.testing.dragUp
import co.herod.adbwrapper.testing.testHelper

fun main(args: Array<String>) {

    val device = AdbDeviceManager.getAllDevices().first()

    println("connected this device $device")

    device.testHelper().dragUp({ width -> width / 2 }, 0.3)

//    device.subscribeUiNodesSource().doOnNext { uiNode ->
//
//        println("$uiNode")
//
//        if ("Google" in uiNode.text) {
//            device.tap(uiNode)
//        }
//    }.blockingSubscribe()
}


