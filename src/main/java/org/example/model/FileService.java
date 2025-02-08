package org.example.model;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class FileService {

    private boolean isFileNotExists(String pathFile) {
        Path path;
        try {
            path = Path.of(pathFile);
        } catch (InvalidPathException e) {
            System.err.println("Invalid file path: " + pathFile);
            return true;
        }

        return Files.notExists(path) || !Files.isRegularFile(path);
    }

    public String readInFromFile(String pathFile) {
        if (pathFile == null || pathFile.isBlank()) {
            throw new IllegalArgumentException("File path cannot be null or blank");
        }
        if (isFileNotExists(pathFile)) {
            throw new IllegalArgumentException("Invalid file path or invalid format: " + pathFile);
        }
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(pathFile), StandardCharsets.UTF_8))) {
            String text;
            while ((text = reader.readLine()) != null) {
                builder.append(text).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("File reading error: " + pathFile, e);
        }
        return builder.toString();
    }

    public void writeOutToFile(String text, String pathFile) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Text is null or empty");
        }

        if (pathFile == null || pathFile.isBlank()) {
            throw new IllegalArgumentException("File path cannot be null or blank");
        }

        if (isFileNotExists(pathFile)) {
            createFile(pathFile);
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathFile), StandardCharsets.UTF_8))) {
            bufferedWriter.write(text);
        } catch (IOException e) {
            throw new IllegalArgumentException("File write error: " + pathFile, e);
        }
    }

    public void createFile(String pathFile) {
        if (pathFile == null || pathFile.isBlank()) {
            throw new IllegalArgumentException("File path cannot be null or blank");
        }

        if (Files.exists(Path.of(pathFile))) {
            throw new IllegalArgumentException("File already exists: " + pathFile);
        }

        try {
            Files.createFile(Path.of(pathFile));
        } catch (IOException e) {
            throw new IllegalArgumentException("File creation error: " + pathFile, e);
        }
    }
}
