package co.herod.adbwrapper.util

import co.herod.adbwrapper.model.DumpsysEntry

fun DumpsysEntry.hasPositiveValue(): Boolean = value.sanitised().hasPositiveValue()

fun hasPositiveValue(entry: Map.Entry<String, String>): Boolean = entry.value.sanitised().hasPositiveValue()

private fun String.sanitised(): String = trim().toLowerCase()

private fun String.hasPositiveValue(): Boolean = this == "on" || this == "1" || "true" in this
