package co.herod.adbwrapper.model

import co.herod.adbwrapper.util.UiHierarchyHelper

class AdbUiNode(private val nodeString: String) {

    internal val bounds: Array<Int>? by lazy { UiHierarchyHelper.extractBoundsInts(nodeString)?.blockingSingle() }

    val width: Int? by lazy { bounds?.let { UiHierarchyHelper.getWidth(it) } }

    val height: Int? by lazy { bounds?.let { UiHierarchyHelper.getHeight(it) } }

    val text by lazy { UiHierarchyHelper.extractText(nodeString) }

    override fun toString() = "AdbUiNode{nodeString='$nodeString'}"

}
