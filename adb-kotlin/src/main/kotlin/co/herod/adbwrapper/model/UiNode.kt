@file:Suppress("unused")

package co.herod.adbwrapper.model

import co.herod.adbwrapper.util.UiHelper
import co.herod.kotlin.ext.containsIgnoreCase

@Suppress("MemberVisibilityCanPrivate")
class UiNode(private val nodeString: String) {

    val selected: Boolean by lazy {
        UiHelper.extractProperty(nodeString, "selected").hasPositiveValue()
    }

    val password: Boolean by lazy {
        UiHelper.extractProperty(nodeString, "password").hasPositiveValue()
    }

    val longClickable: Boolean by lazy {
        UiHelper.extractProperty(nodeString, "long-clickable").hasPositiveValue()
    }

    val scrollable: Boolean by lazy {
        UiHelper.extractProperty(nodeString, "scrollable").hasPositiveValue()
    }

    val focused: Boolean by lazy {
        UiHelper.extractProperty(nodeString, "focused").hasPositiveValue()
    }

    val focusable: Boolean by lazy {
        UiHelper.extractProperty(nodeString, "focusable").hasPositiveValue()
    }

    val enabled: Boolean by lazy {
        UiHelper.extractProperty(nodeString, "enabled").hasPositiveValue()
    }

    val clickable: Boolean by lazy {
        UiHelper.extractProperty(nodeString, "clickable").hasPositiveValue()
    }

    val checked: Boolean by lazy {
        UiHelper.extractProperty(nodeString, "checked").hasPositiveValue()
    }

    val checkable: Boolean by lazy {
        UiHelper.extractProperty(nodeString, "checkable").hasPositiveValue()
    }

    // e.g. index="10"
    val uiIndex: Int by lazy {
        UiHelper.extractProperty(nodeString, "index").let { Integer.parseInt(it) }
    }

    val bounds: UiBounds by lazy {
        UiHelper.extractBoundsInts(nodeString).let { UiBounds(it) }
    }

    val text: String by lazy {
        UiHelper.extractProperty(nodeString, "text")
    }

    // e.g. package="com.android.calculator2"
    val packageName: String by lazy {
        UiHelper.extractProperty(nodeString, "package")
    }

    // e.g. content-desc=""
    val contentDescription by lazy {
        UiHelper.extractProperty(nodeString, "content-desc")
    }

    // e.g. class="android.widget.Button"
    val uiClass by lazy {
        UiHelper.extractProperty(nodeString, "class")
    }

    // e.g. resource-id="com.android.calculator2:id/digit_0"
    val resourceId by lazy {
        UiHelper.extractProperty(nodeString, "resource-id")
    }

    val visible: Boolean by lazy {
        bounds.let { it.width > 0 && it.height > 0 }
    }

    val isTextField: Boolean by lazy {
        arrayOf("EditText", "TextInput").any { it in uiClass }
    }

    val isButton: Boolean by lazy {
        arrayOf("Button", "Text").any { it in uiClass } && (clickable || longClickable)
    }

    fun contains(charSequence: String): Boolean = this
            .toString()
            .containsIgnoreCase(charSequence)

    override fun toString(): String {
        return "UiNode(nodeString='$nodeString')"
    }

    private fun String.hasPositiveValue(): Boolean = this == "on" || this == "1" || "true" in this
}