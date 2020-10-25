package threading._01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lferracini
 * @project = java-thread
 * @since <pre>24/10/2020 23:12</pre>
 */

public class Add implements Runnable {
    private String inFile, outFile;

    public Add(String inFile, String outFile) {
        this.inFile = inFile;
        this.outFile = outFile;
    }

    public void doAdd() throws IOException {
        String line;
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inFile))) {
            while ((line = reader.readLine()) != null) {

                lines.add(line);
            }
        }
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outFile))) {
            lines.forEach(e-> {
                try {
                    writer.write(e);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
        }
    }

    @Override
    public void run() {
        try {
            doAdd();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
