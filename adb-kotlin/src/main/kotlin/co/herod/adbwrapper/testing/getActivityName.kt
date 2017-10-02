@file:Suppress("unused")

package co.herod.adbwrapper.testing

import co.herod.adbwrapper.model.DumpsysEntry

fun AdbDeviceTestHelper.getActivityName(fullyQualify: Boolean = false): String = with(adbDevice) {

    getDumpsysWindowFocus()
            .map(extract(fullyQualify))
            .distinctUntilChanged()
            .blockingFirst()
}

private fun extract(fullyQualify: Boolean = false): (DumpsysEntry) -> String = {
    when {
        fullyQualify -> it.value
                .substringBeforeLast('}')
                .substringBeforeLast(' ')
                .substringAfterLast(' ')
        else -> it.value
                .substringAfterLast('.')
                .substringBefore('}')
                .substringBefore(' ')
    }
}

