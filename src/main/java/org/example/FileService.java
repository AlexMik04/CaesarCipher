package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileService {

    private boolean isDirectoryExists(String pathFile) {
        if (pathFile == null || pathFile.isEmpty()) {
            return false;
        }

        Path path = Paths.get(pathFile);
        return path.isAbsolute() && Files.isDirectory(path.getParent());
    }

    private boolean isFileExists(String pathFile) {
        if (pathFile == null || pathFile.isEmpty()) {
            return false;
        }

        Path path = Paths.get(pathFile);
        return Files.exists(path) && Files.isRegularFile(path);
    }

    public String readInFromFile(String pathFile) {
        if (!isFileExists(pathFile)) {
            throw new IllegalArgumentException("File not specified or has an invalid format");
        }
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(pathFile), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("File reading error: " + pathFile, e);
        }
        System.out.println("Text from the file " + pathFile + " has been successfully read");
        return builder.toString();
    }

    public void writeOutToFile(String line, String pathFile) {
        if (!isDirectoryExists(pathFile)) {
            throw new IllegalArgumentException("File not specified or has an invalid format");
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathFile), StandardCharsets.UTF_8))) {
            bufferedWriter.write(line);
            System.out.println("The file " + pathFile + " has been successfully created and written");
        } catch (IOException e) {
            throw new IllegalArgumentException("File write error: " + pathFile, e);
        }
    }
}
