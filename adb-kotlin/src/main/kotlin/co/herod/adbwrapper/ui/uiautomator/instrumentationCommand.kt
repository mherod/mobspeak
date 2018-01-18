/*
 * Copyright (c) 2018. Herod
 */

package co.herod.adbwrapper.ui.uiautomator

import co.herod.adbwrapper.S

val instrumentationCommand: String by lazy {
    "${S.SHELL} am instrument -w -e package ${S.PACKAGE_UIAUTOMATOR} ${S.PACKAGE_UIAUTOMATOR_TEST}/${S.ANDROID_TEST_RUNNER}"
}