/*
 * Copyright (c) 2018. Herod
 */

@file:Suppress("MemberVisibilityCanPrivate")

package co.herod.adbwrapper.model

class UiBounds(private val coordinatesIn: IntArray?) {

    val centreX: Int by lazy { (x1 + x2) / 2 }
    val centreY: Int by lazy { (y1 + y2) / 2 }
    val width: Int by lazy { x2 - x1 }
    val height: Int by lazy { y2 - y1 }
    val coordinates: IntArray by lazy { intArrayOf(x1, x2, y1, y2) }

    val x1: Int by lazy {
        when {
            coordinatesIn?.size == 4 -> coordinatesIn[0]
            else -> 0
        }
    }

    val y1: Int by lazy {
        when {
            coordinatesIn?.size == 4 -> coordinatesIn[1]
            else -> 0
        }
    }

    val x2: Int by lazy {
        when {
            coordinatesIn?.size == 4 -> coordinatesIn[2]
            coordinatesIn?.size == 2 -> coordinatesIn[0]
            else -> 0
        }
    }

    val y2: Int by lazy {
        when {
            coordinatesIn?.size == 4 -> coordinatesIn[3]
            coordinatesIn?.size == 2 -> coordinatesIn[1]
            else -> 0
        }
    }
}