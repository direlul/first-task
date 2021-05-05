package ru.saburov;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    static List<File> getTextFiles(File root) {
        List<File> result = new ArrayList<>();

        if (root.isFile()) {
            return List.of(root);
        }

        File[] filesList = root.listFiles();

        if (filesList == null) {
            return List.of();
        }

        for (File f : filesList) {
            if (f.isDirectory()) {
                result.addAll(getTextFiles(f));
            } else {
                result.add(f);
            }
        }

        return result;
    }

    public static void mergeTextFiles(List<File> files) throws IOException {
        File resultFile = new File("result.txt");

        if (resultFile.createNewFile()) {
            System.out.println("File created");
        }

        PrintWriter printWriter = new PrintWriter(resultFile);
        BufferedReader bufferedReader = null;
        String line;

        for (var file : files) {
            bufferedReader = new BufferedReader(new FileReader(file));

            while ((line = bufferedReader.readLine()) != null) {
                printWriter.println(line);
            }
        }

        if (bufferedReader != null) {
            bufferedReader.close();
        }

        printWriter.flush();
        printWriter.close();
    }

    public static void main(String[] args) throws IOException {
        File rootFile = new File("basedir");
        List<File> textFiles = getTextFiles(rootFile);

        textFiles = textFiles.stream()
                .sorted(Comparator.comparing(File::getName))
                .collect(Collectors.toList());

        for (File textFile : textFiles) {
            System.out.println(textFile.getName());
        }

        mergeTextFiles(textFiles);

    }
}
