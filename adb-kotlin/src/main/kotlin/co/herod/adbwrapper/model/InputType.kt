/*
 * Copyright (c) 2018. Herod
 */

package co.herod.adbwrapper.model

enum class InputType(private val s: String) : CharSequence {
    TAP("tap"),
    TEXT("text"),
    KEY_EVENT("keyevent"),
    SWIPE("swipe");

    override val length: Int
        get() = s.length

    override fun get(index: Int): Char = s[index]
    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence = s.subSequence(startIndex, endIndex)
    override fun toString(): String = s
}