package co.herod.adbwrapper.model

import co.herod.adbwrapper.util.UiHierarchyHelper

class UiBounds(val coordinates: Array<Int>?){

    val centreX: Int by lazy {
        coordinates?.let { UiHierarchyHelper.centreX(it) } ?: 0
    }

    val centreY: Int by lazy {
        coordinates?.let { UiHierarchyHelper.centreY(it) } ?: 0
    }

    val width: Int by lazy {
        coordinates?.let { it[2] - it[0] } ?: 0
    }

    val height: Int by lazy {
        coordinates?.let { it[3] - it[1] } ?: 0
    }
}