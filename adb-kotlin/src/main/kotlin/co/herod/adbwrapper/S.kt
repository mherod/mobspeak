/*
 * Copyright (c) 2018. Herod
 */

package co.herod.adbwrapper

class S {
    companion object {
        const val ADB = "adb"

        const val SHELL = "shell"

        const val DISABLE = "disable"
        const val ENABLE = "enable"

        const val SERVICE = "svc"

        const val DEVICES = "devices"

        const val INTENT_CATEGORY_LAUNCHER = "android.intent.category.LAUNCHER"
        const val INTENT_ACTION_VIEW = "android.intent.action.VIEW"

        const val DEVICE_CONNECTED_DEVICE = "device"
        const val DEVICE_EMULATOR = "emulator"

        const val UI_DUMP_XML_PATH = "/sdcard/window_dump.xml"

        const val DUMPSYS = "dumpsys"
        const val INPUT = "input"

        const val PIDOF = "pidof"

        const val PROPERTY_SCREEN_STATE = "mScreenState"
        const val PROPERTY_CURRENT_FOCUS = "mCurrentFocus"
        const val PROPERTY_FOCUSED_APP = "mFocusedApp"


        const val PROPERTY_SHOW_REQUESTED = "mShowRequested"
        const val ANDROID_TEST_RUNNER = "android.support.test.runner.AndroidJUnitRunner"
        const val PACKAGE_UIAUTOMATOR = "com.github.uiautomator"
        const val PACKAGE_UIAUTOMATOR_TEST = "$PACKAGE_UIAUTOMATOR.test"

    }

}
