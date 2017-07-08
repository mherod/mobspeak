package co.herod.adbwrapper;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class AdbShell {

    public static void main(final String[] args) {

        final ProcessBuilder processBuilder = new ProcessBuilder()
                .command("adb", "shell")
                .redirectErrorStream(true);

        final Process process;
        try {
            process = processBuilder.start();
        } catch (final IOException e) {
            e.printStackTrace();
            return;
        }

        try {
            doProcessStuff(process);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private static void doProcessStuff(final Process process) throws IOException {

        Flowable.just(process)
                .subscribeOn(Schedulers.newThread())
                .doOnNext(p -> adbShellCommand(p.getOutputStream(), p.getInputStream(), "echo hi")
                        .subscribe(System.out::println, throwable -> {
                        }))
                .delay(3, TimeUnit.SECONDS)
                .doOnNext(p -> adbShellCommand(p.getOutputStream(), p.getInputStream(), "echo number")
                        .subscribe(System.out::println, throwable -> {
                        }))
                .timeout(10, TimeUnit.SECONDS)
                .blockingSubscribe(a -> {
                }, throwable -> {
                });
    }

    private static Flowable<Object> adbShellCommand(final OutputStream outputStream, final InputStream inputStream, final String s) {

        return Flowable.fromPublisher(s1 -> {

            final PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.println(s);
            printWriter.flush();

            String line;

            try {
                final BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

                while ((line = br.readLine()) != null) {
                    s1.onNext(line);
                }
            } catch (final IOException e) {
                s1.onError(e);
            }
            s1.onComplete();

        }).timeout(1, TimeUnit.SECONDS);
    }
}
