package threading._01;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
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
 * @since <pre>25/10/2020 17:34</pre>
 */

class AddTest {


    private List<String> inFilesList = Arrays.asList("filetools1.txt", "filetools2.txt",
            "filetools3.txt", "filetools4.txt", "filetools5.txt",
            "filetools6.txt");
    private List<String> outFilesList = Arrays.asList("output-file1.txt", "output-file2.txt",
            "output-file3.txt", "output-file4.txt", "output-file5.txt",
            "output-file6.txt");
    //execThredWithArray(inFilesList, outFilesList);
    //execThreadWithExecutor(inFilesList, outFilesList);
    //execThreadWithFuture(inFilesList);

    @Test
    void execThreadWithExecutor() {
        ExecutorService es = Executors.newFixedThreadPool(3);
        for (int i = 0; i < inFilesList.size(); i++) {
            threading._01.Add add = new threading._01.Add(inFilesList.get(i), outFilesList.get(i));
            es.submit(add);
        }

        try {
            es.shutdown();
            es.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    void execThredWithArray() throws InterruptedException {
        Thread[] threads = new Thread[inFilesList.size()];
        for (int i = 0; i < inFilesList.size(); i++) {
            threading._01.Add add = new threading._01.Add(inFilesList.get(i), outFilesList.get(i));
//            add.doAdd();
            threads[i] = new Thread(add);
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join(); //Blocks waiting for thread completion
        }
    }
}