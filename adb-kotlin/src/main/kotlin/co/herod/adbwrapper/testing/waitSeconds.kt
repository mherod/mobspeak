package co.herod.adbwrapper.testing

@JvmOverloads
fun waitSeconds(waitSeconds: Int = 3) = try {
    Thread.sleep((waitSeconds * 1000).toLong())
} catch (ignored: InterruptedException) {
}