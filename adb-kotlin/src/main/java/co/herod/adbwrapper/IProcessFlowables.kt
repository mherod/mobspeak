package co.herod.adbwrapper

import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by matthewherod on 19/08/2017.
 */
interface IProcessFactory {
    fun observableProcess(processBuilder: ProcessBuilder?): Observable<String>
    fun blocking(processBuilder: ProcessBuilder, timeout: Int, timeUnit: TimeUnit)
    fun blocking(processBuilder: ProcessBuilder?)
}