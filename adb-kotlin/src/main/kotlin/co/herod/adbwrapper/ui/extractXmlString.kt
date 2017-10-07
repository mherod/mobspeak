package co.herod.adbwrapper.ui

fun extractXmlString(it: String) = it
        .substringAfter('<')
        .substringBeforeLast('>')
        .let {
            when { it.endsWith('>') -> it; else -> "$it>" }
        }