package co.herod.adbwrapper

import io.reactivex.Observable

/**
 * Created by matthewherod on 19/08/2017.
 */
interface IProcessFactory {
    fun observableProcess(processBuilder: ProcessBuilder): Observable<String>
    fun observableShellProcess(adbCommand: AdbCommand): Observable<String>
}