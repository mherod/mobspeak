package co.herod.adbwrapper

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.concurrent.TimeUnit

object AdbShell {

    @JvmStatic
    fun main(args: Array<String>) {

        val processBuilder = ProcessBuilder()
                .command("adb", "shell")
                .redirectErrorStream(true)

        val process: Process
        try {
            process = processBuilder.start()
        } catch (e: IOException) {
            e.printStackTrace()
            return
        }

        try {
            doProcessStuff(process)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    @Throws(IOException::class)
    private fun doProcessStuff(process: Process) {

        Flowable.just(process)
                .subscribeOn(Schedulers.newThread())
                .doOnNext {
                    adbShellCommand(it.outputStream, it.inputStream, "echo hi")
                            .subscribe({ println(it) }) { }
                }
                .delay(3, TimeUnit.SECONDS)
                .doOnNext {
                    adbShellCommand(it.outputStream, it.inputStream, "echo number")
                            .subscribe({ println(it) }) { }
                }
                .timeout(10, TimeUnit.SECONDS)
                .blockingSubscribe({ }) { }
    }

    private fun adbShellCommand(outputStream: OutputStream, inputStream: InputStream, s: String): Flowable<Any> =
            Flowable.fromPublisher<Any> { s1 ->

                val bufferedWriter = outputStream.bufferedWriter()

                bufferedWriter.write(s + "\n"); bufferedWriter.flush()

                inputStream.bufferedReader().forEachLine { s1.onNext(it) }; s1.onComplete()

            }.timeout(1, TimeUnit.SECONDS)
}
