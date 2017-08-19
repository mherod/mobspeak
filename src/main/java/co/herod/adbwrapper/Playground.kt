package co.herod.adbwrapper

/**
 * Created by matthewherod on 23/04/2017.
 */
object Playground {

    @JvmStatic
    fun main(args: Array<String>) {

        AdbStreams.streamAdbCommands().subscribe(::println)

        val adbDevice = AdbDeviceManager.getConnectedDevice()

        AdbDeviceActions.turnDeviceScreenOn(adbDevice)

        AdbUi.startStreamingUiHierarchy(adbDevice).subscribe()

        AdbBusManager.ADB_UI_HIERARCHY_BUS.doOnNext(::println).subscribe()

        AdbBusManager.ADB_UI_NODE_BUS.doOnNext(::println).subscribe()

        // wait for terminate
        AdbBusManager.ADB_BUS.blockingSubscribe()
    }
}
