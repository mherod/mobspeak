/*
 * Copyright (c) 2018. Herod
 */

package co.herod.adbwrapper.testing

import co.herod.adbwrapper.device.input.swipe

@JvmOverloads
fun AdbDeviceTestHelper.dragRight(
        heightFunction: ((Int) -> Int),
        edgeOffset: Double = 0.0
) = with(adbDevice) {
    windowBounds.run {
        heightFunction(height).let {
            swipe((width * edgeOffset).toInt(),
                    it,
                    (width * (1.0 - edgeOffset)).toInt(),
                    it)
        }
    }
}