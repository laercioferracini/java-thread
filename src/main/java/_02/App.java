package _02;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author lferracini
 * @project = java-thread
 * @since <pre>24/10/2020 23:17</pre>
 */

public class App {
    public static void main(String[] args) throws InterruptedException, IOException {
        List<String> listInFiles = Arrays.asList("onlinefiletools(1).txt", "onlinefiletools(2).txt",
                "onlinefiletools(3).txt", "onlinefiletools(4).txt", "onlinefiletools(5).txt",
                "onlinefiletools(6).txt");
        List<String> listOutFiles = Arrays.asList("output-onlinefiletools(1).txt", "output-onlinefiletools(2).txt",
                "output-onlinefiletools(3).txt", "output-onlinefiletools(4).txt", "output-onlinefiletools(5).txt",
                "output-onlinefiletools(6).txt");
//        execThredWithArray(listInFiles, listOutFiles);
        execThreadWithExecutor(listInFiles, listOutFiles);

    }

    private static void execThreadWithExecutor(List<String> listInFiles, List<String> listOutFiles) {
        ExecutorService es = Executors.newFixedThreadPool(3);
        Future<Integer>[] results = new Future[listInFiles.size()];
        for (int i = 0; i < listInFiles.size(); i++) {
            Add add = new Add(listInFiles.get(i));
//            add.doAdd();
            results[i] = es.submit(add);
        }
        for (Future<Integer> result : results) {
            int value = 0; //blocks until return value avalaible
            try {
                value = result.get();
                System.out.println("Total: "+ value);
            } catch (ExecutionException e) {
                Throwable t = e.getCause();
            }catch (Exception e){
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
