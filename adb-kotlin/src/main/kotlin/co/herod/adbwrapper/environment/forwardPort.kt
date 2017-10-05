package co.herod.adbwrapper.environment

import co.herod.adbwrapper.command
import co.herod.adbwrapper.model.AdbDevice
import co.herod.kotlin.log

@JvmOverloads
fun AdbDevice.forwardPort(local: Int = 9008, remote: Int = 9008) =
        command("forward tcp:$local tcp:$remote")
                .doOnError { println(it) }
                .doOnComplete { log("Successfully forwarded local:$local to remote:$remote") }
                .retry(1)
                .blockingSubscribe()