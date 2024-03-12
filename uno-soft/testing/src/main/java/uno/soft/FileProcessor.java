package uno.soft;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

        List<List<String>> lines = readFile();
        List<List<List<String>>> groupedLines = groupingLines(lines); // лист сверху - это список групп, следущий -
                                                                      // список строк в группе, последний - список
                                                                      // подстрок в строке

        printLines(groupedLines);

        long endTime = System.currentTimeMillis();
        long freeMemoryAfter = runtime.freeMemory();

        System.out.println();
        System.out.println("Time is " + (endTime - startTime));
        System.out
                .println("Memory is " + Math.abs(freeMemoryAfter - freeMemoryBefore) / (1024.0 * 1024.0 * 1024.0));

    }

    private List<List<List<String>>> groupingLines(List<List<String>> lines) {
        List<List<List<String>>> groups = new ArrayList<>();
        while (!lines.isEmpty()) { // мы проходим по всему списку, пока не опустошим его
            List<List<String>> currentGroup = new ArrayList<>();

            Map<Integer, Set<String>> columnValues = new HashMap(); // обработка первой строки
            for (int i = 0; i < lines.get(0).size(); i++) {
                Set<String> values = new HashSet<>();
                values.add(lines.get(0).get(i));
                columnValues.put(i, values);
            }

            boolean isAdded = false;

            while (!isAdded) {
                isAdded = false;
                List<List<String>> notUsedLines = new ArrayList<>();
                for (List<String> line : lines) {
                    if (isLineAtGroup(line, columnValues)) {
                        addSubsToSets(line, columnValues);
                        currentGroup.add(line);
                        isAdded = true;
                    } else {
                        notUsedLines.add(line);
                    }
                }
                lines = notUsedLines;
            }
            groups.add(currentGroup);
        }
        return groups;
    }

    private List<List<String>> readFile() throws IOException {
        List<List<String>> lines = new ArrayList<>();

        File file = new File(filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                List<String> splitedLine = Arrays.asList(line.split(";")); // разделенная строк
                boolean isOk = true;
                for (String subLine : splitedLine) { // смотрим нет ли неправильных форматов
                    if (countOccurrences(subLine, '"') != 2) { // подумать еще варианты падения
                        isOk = false;
                        break;
                    }
                }
                if (isOk) {
                    lines.add(splitedLine);
                }
            }
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

    private boolean isLineAtGroup(List<String> line, Map<Integer, Set<String>> columnValues) {
        boolean isLineAtGroup = false;
        Set<Integer> columnIndexes = columnValues.keySet();
        for (int i = 0; i < line.size(); i++) {
            if (columnIndexes.contains(i)) {
                if (columnValues.get(i).contains(line.get(i))) {
                    return true;
                }
            }
        }
        return isLineAtGroup;
    }

    private void addSubsToSets(List<String> line, Map<Integer, Set<String>> columnValues) {
        Set<Integer> columnIndexes = columnValues.keySet();
        for (int i = 0; i < line.size(); i++) {
            Set<String> values = null;
            if (columnIndexes.contains(i)) {
                values = columnValues.get(i);
            } else {
                values = new HashSet<>();
            }
            values.add(line.get(i));
            columnValues.put(i, values);
        }
    }

    private void printLines(List<List<List<String>>> groupedLines) {
        for (int i = 0; i < groupedLines.size(); i++) {
            System.out.println("Группа " + (i + 1));
            for (List<String> line : groupedLines.get(i)) {
                System.out.println(String.join(";", line));
            }
            System.out.println();
        }
    }
}
