/*
 * Copyright (c) 2018. Herod
 */

package co.herod.adbwrapper.testing

import co.herod.adbwrapper.device.input.swipe

@JvmOverloads
fun AdbDeviceTestHelper.dragUp(
        widthFunction: ((Int) -> Int),
        edgeOffset: Double = 0.0
) = with(adbDevice) {
    windowBounds.run {
        widthFunction(width).let {
            swipe(
                    it,
                    (height * (1.0 - edgeOffset)).toInt(),
                    it,
                    (height * edgeOffset).toInt()
            )
        }
    }
}