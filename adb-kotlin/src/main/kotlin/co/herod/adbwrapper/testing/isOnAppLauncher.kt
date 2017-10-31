@file:Suppress("unused")

package co.herod.adbwrapper.testing

private const val NEXUS_LAUNCHER = "NexusLauncher"
private const val LAUNCHER = "Launcher"

fun AdbDeviceTestHelper.isOnAppLauncher() =
        isOnActivity(NEXUS_LAUNCHER) || isOnActivity(LAUNCHER)