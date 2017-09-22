@file:Suppress("unused")

package co.herod.adbwrapper.model

import co.herod.adbwrapper.util.UiHelper
import co.herod.kotlin.ext.containsIgnoreCase

@Suppress("MemberVisibilityCanPrivate")
class UiNode(private val nodeString: String) {

    val bounds: UiBounds by lazy {
        UiHelper.extractBoundsInts(nodeString).let { UiBounds(it) }
    }

    val text: String by lazy {
        UiHelper.extractProperty(nodeString, "text")
    }

    val packageName: String by lazy {
        UiHelper.extractProperty(nodeString, "package")
    }

    val contentDescription by lazy {
        UiHelper.extractProperty(nodeString, "content-desc")
    }

    val uiClass by lazy {
        UiHelper.extractProperty(nodeString, "class")
    }

    val visible: Boolean by lazy {
        bounds.let { it.width > 0 && it.height > 0 }
    }

    fun contains(charSequence: String): Boolean = this
            .toString()
            .containsIgnoreCase(charSequence)

    override fun toString(): String {
        return "UiNode(nodeString='$nodeString')"
    }
}
