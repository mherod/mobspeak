package co.herod.adbwrapper.model

import co.herod.adbwrapper.util.UiHierarchyHelper

@Suppress("MemberVisibilityCanPrivate")
class UiNode(private val nodeString: String) {

    val bounds: UiBounds by lazy {
        UiHierarchyHelper.extractBoundsInts(nodeString).blockingGet()
    }

    val text: String by lazy {
        UiHierarchyHelper.extractText(nodeString)
    }

    val visible: Boolean by lazy {
        bounds.let { it.width > 0 && it.height > 0 }
    }

    override fun toString(): String {
        return "UiNode(nodeString='$nodeString')"
    }

    fun adbUiNodeMatches(charSequence: String): Boolean = this
            .toString()
            .toLowerCase()
            .contains(charSequence)
}
