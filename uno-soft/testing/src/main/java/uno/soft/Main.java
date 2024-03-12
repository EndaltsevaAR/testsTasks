package uno.soft;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        if (args.length < 1) {
            System.err.println("There is no filename");
            System.exit(-1);
        }
        long startTime = System.currentTimeMillis();
        long freeMemoryBefore = runtime.freeMemory();

        Set<String> lines = new TreeSet<>();
        File file = new File(args[0]);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            System.out.println(lines.toString());
            long endTime = System.currentTimeMillis();
            long freeMemoryAfter = runtime.freeMemory();
            System.out.println();
            System.out.println("Time is " + (endTime - startTime));
            System.out
                    .println("Memory is " + Math.abs(freeMemoryAfter - freeMemoryBefore) / (1024.0 * 1024.0 * 1024.0));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
