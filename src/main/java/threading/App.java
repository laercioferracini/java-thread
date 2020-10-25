package threading;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import threading._02.Add;

/**
 * @author lferracini
 * @project = java-thread
 * @since <pre>24/10/2020 23:17</pre>
 */

public class App {
    public static void main(String[] args) throws InterruptedException, IOException {
        List<String> inFilesList = Arrays.asList("filetools1.txt", "filetools2.txt",
                "filetools3.txt", "filetools4.txt", "filetools5.txt",
                "filetools6.txt");
        List<String> outFilesList = Arrays.asList("output-file1.txt", "output-file2.txt",
                "output-file3.txt", "output-file4.txt", "output-file5.txt",
                "output-file6.txt");
        //execThredWithArray(inFilesList, outFilesList);
        //execThreadWithExecutor(inFilesList, outFilesList);
        execThreadWithFuture(inFilesList);

    }

    private static void execThreadWithExecutor(List<String> listInFiles, List<String> listOutFiles) {
        ExecutorService es = Executors.newFixedThreadPool(3);
        for (int i = 0; i < listInFiles.size(); i++) {
            threading._01.Add add = new threading._01.Add(listInFiles.get(i), listOutFiles.get(i));
            es.submit(add);
        }

        try {
            es.shutdown();
            es.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void execThredWithArray(List<String> listInFiles, List<String> listOutFiles) throws InterruptedException {
        Thread[] threads = new Thread[listInFiles.size()];
        for (int i = 0; i < listInFiles.size(); i++) {
            threading._01.Add add = new threading._01.Add(listInFiles.get(i), listOutFiles.get(i));
//            add.doAdd();
            threads[i] = new Thread(add);
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join(); //Blocks waiting for thread completion
        }
    }

    private static void execThreadWithFuture(List<String> listInFiles) {
        ExecutorService es = Executors.newFixedThreadPool(3);
        Future<Integer>[] results = new Future[listInFiles.size()];
        for (int i = 0; i < listInFiles.size(); i++) {
            Add add = new Add(listInFiles.get(i));

            results[i] = es.submit(add);
        }
        for (Future<Integer> result : results) {
            int value = 0; //blocks until return value avalaible
            try {
                value = result.get();
                System.out.println("Total: " + value);
            } catch (ExecutionException e) {
                Throwable t = e.getCause();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        try {
            es.shutdown();
            es.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
