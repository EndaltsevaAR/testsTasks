package uno.soft;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
        List<List<List<String>>> groupedLines = groupingLines(lines);

        printLines(groupedLines);

        long endTime = System.currentTimeMillis();
        long freeMemoryAfter = runtime.freeMemory();

        System.out.println();
        System.out.println("Время выполнения программы  " + (endTime - startTime) + " миллисекунд");
        System.out
                .println("Использовано " + Math.abs(freeMemoryAfter - freeMemoryBefore) / (1024.0 * 1024.0 * 1024.0)
                        + " гб памяти");

    }

    private List<List<List<String>>> groupingLines(List<List<String>> lines) {
        List<List<List<String>>> groups = new ArrayList<>();
        while (!lines.isEmpty()) {
            List<List<String>> currentGroup = new ArrayList<>();

            Map<Integer, Set<String>> columnValues = new HashMap();
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
                List<String> splitedLine = Arrays.asList(line.split(";"));
                boolean isOk = true;
                for (String subLine : splitedLine) {
                    if (countOccurrences(subLine, '"') != 2) {
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
                if (columnValues.get(i).contains(line.get(i)) && (!line.get(i).equals(""))) {
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

            if (!line.get(i).equals("")) {
                values.add(line.get(i));
                columnValues.put(i, values);
            }
        }
    }

    private void printLines(List<List<List<String>>> groupedLines) {
        String filePath = "output.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            Iterator<List<List<String>>> iterator = groupedLines.iterator();
            while (iterator.hasNext()) {
                List<List<String>> group = iterator.next();
                if (group.size() <= 1) {
                    iterator.remove();
                }
            }

            Collections.sort(groupedLines, Comparator.comparingInt(List::size));
            System.out.println("Всего " + groupedLines.size() + " групп");

            for (int i = groupedLines.size() - 1; i >= 0; i--) {
                writer.write("Группа " + (i + 1));
                writer.newLine();
                for (List<String> line : groupedLines.get(i)) {
                    writer.write(String.join(";", line));
                    writer.newLine();
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
