package co.herod.adbwrapper.model

import co.herod.adbwrapper.util.UiHierarchyHelper

@Suppress("MemberVisibilityCanPrivate")
class AdbUiNode(private val nodeString: String) {

    val bounds: Array<Int>? by lazy {
        UiHierarchyHelper.extractBoundsInts(nodeString)?.blockingSingle()
    }

    val width: Int by lazy {
        bounds?.let { it[2] - it[0] } ?: 0
    }

    val height: Int by lazy {
        bounds?.let { it[3] - it[1] } ?: 0
    }

    val centreX: Int by lazy {
        bounds?.let(UiHierarchyHelper::centreX) ?: 0
    }

    val centreY: Int by lazy {
        bounds?.let(UiHierarchyHelper::centreY) ?: 0
    }

    val text: String by lazy {
        UiHierarchyHelper.extractText(nodeString)
    }

    val visible: Boolean by lazy {
        width > 0 && height > 0
    }

    override fun toString(): String {
        return "AdbUiNode(nodeString='$nodeString')"
    }
}
