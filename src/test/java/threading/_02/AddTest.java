package threading._02;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

/**
 * @author lferracini
 * @project = java-thread
 * @since <pre>25/10/2020 17:44</pre>
 */

class AddTest {
    List<String> inFilesList = Arrays.asList("filetools1.txt", "filetools2.txt",
            "filetools3.txt", "filetools4.txt", "filetools5.txt",
            "filetools6.txt");


    @Test
    void execThreadWithFuture() {
        ExecutorService es = Executors.newFixedThreadPool(3);
        Future<Integer>[] results = new Future[inFilesList.size()];

        var values = new int[6];
        values[0] = 25951;
        values[1] = 25951;
        values[2] = 25951;
        values[3] = 25951;
        values[4] = 25951;
        values[5] = 77853;

        for (int i = 0; i < inFilesList.size(); i++) {
            threading._02.Add add = new threading._02.Add(inFilesList.get(i));

            results[i] = es.submit(add);
        }
        int i = 0;
        for (Future<Integer> result : results) {
            int value;
            try {
                value = result.get();//blocks until return value avalaible
                System.out.println("Total: " + value);
                assertEquals(values[i++], value);
            } catch (ExecutionException e) {
                Throwable t = e.getCause();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
//        Arrays.asList(results).forEach(result -> {
//            int i = 0;
//            int value = 0;
//            try {
//                value = result.get();
//
//            } catch (ExecutionException e) {
//                Throwable t = e.getCause();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
        try {
            es.shutdown();
            es.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}