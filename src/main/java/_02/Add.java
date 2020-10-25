package _02;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

/**
 * @author lferracini
 * @project = java-thread
 * @since <pre>24/10/2020 23:12</pre>
 */

public class Add implements Callable<Integer> {
    private String inFile;

    public Add(String inFile) {
        this.inFile = inFile;
    }

    public int doAdd() throws IOException {
        int total = 0;
        String line = null;
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inFile))) {
            while ((line = reader.readLine()) != null)
                total += 1;
        }
        return total;
    }

    @Override
    public Integer call() throws IOException {

        return doAdd();
    }
}
