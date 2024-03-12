package uno.soft;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class FileProcessor {
    private String filename;

    public FileProcessor(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void run() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        long startTime = System.currentTimeMillis();
        long freeMemoryBefore = runtime.freeMemory();

        Set<List<String>> lines = readFile(); // здесь будут хранится уникальные подстроки по столбам для каждой
                                              // группы
        List<List<Set<String>>> substringGrouping = groupingLines(lines);

        long endTime = System.currentTimeMillis();
        long freeMemoryAfter = runtime.freeMemory();

        System.out.println();
        System.out.println("Time is " + (endTime - startTime));
        System.out
                .println("Memory is " + Math.abs(freeMemoryAfter - freeMemoryBefore) / (1024.0 * 1024.0 * 1024.0));

    }

    private List<List<Set<String>>> groupingLines(Set<List<String>> lines) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'groupingLines'");
    }

    private Set<List<String>> readFile() throws IOException {
        Set<List<String>> lines = new TreeSet<>();

        File file = new File(filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                List<String> splitedLine = Arrays.asList(line.split(";")); // разделенная строк
                boolean isOk = true;
                for (String subLine : splitedLine) { // смотрим нет ли неправильных форматов
                    if (countOccurrences(subLine, '"') != 2) {
                        isOk = false;
                        break;
                    }
                }
                if (isOk) {
                    lines.add(splitedLine);
                }
            }
            System.out.println(lines.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private int countOccurrences(String str, char targetChar) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == targetChar) {
                count++;
            }
        }
        return count;
    }

}
