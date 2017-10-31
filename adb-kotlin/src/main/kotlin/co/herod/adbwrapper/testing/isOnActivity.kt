package co.herod.adbwrapper.testing

fun AdbDeviceTestHelper.isOnActivity(activityName: String) =
        activityName in getActivityName()

fun AdbDeviceTestHelper.isOnActivity(vararg activityNames: String) =
        activityNames.any { isOnActivity(it) }