package org.example.controller;

import org.example.model.FileService;
import org.example.model.cipher.CipherManager;

import java.nio.file.Path;

public class Controller {

    private final FileService service;
    private final CipherManager manager;

    public Controller(FileService service, CipherManager manager) {
        this.service = service;
        this.manager = manager;
    }

    public void run(String command, String filePath, int key) {
        if (command == null || command.isEmpty() || filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("Command or filePath is null or empty");
        }

        switch (command) {
            case "ENCRYPT" -> {
                String line = service.readInFromFile(filePath);
                String encrypt = manager.encrypt(line, key);
                String file = getModifiedFilePath(filePath, command);
                service.writeOutToFile(encrypt, file);
            }
            case "DECRYPT" -> {
                String line = service.readInFromFile(filePath);
                String encrypt = manager.decrypt(line, key);
                String file = getModifiedFilePath(filePath, command);
                service.writeOutToFile(encrypt, file);
            }
            case "BRUTE_FORCE" -> {
                String line = service.readInFromFile(filePath);
                String encrypt = manager.bruteForce(line);
                String file = getModifiedFilePath(filePath, command);
                service.writeOutToFile(encrypt, file);
            }
            default -> System.out.println("Enter a valid command: [ENCRYPT, DECRYPT, BRUTE_FORCE]");
        }
    }

    private String getModifiedFilePath(String filePath, String command) {
        Path path = Path.of(filePath);
        String parentPath = path.getParent().toString(); // Шлях до директорії
        String fileName = path.getFileName().toString(); // Ім'я файлу з command

        if (fileName.contains("[ENCRYPT]") || fileName.contains("[DECRYPT]") || fileName.contains("[BRUTE_FORCE]")) {
            int indexFirst = fileName.lastIndexOf("[") + 1;
            int indexLast = fileName.lastIndexOf("]");

            return parentPath + "/" + fileName.substring(0, indexFirst) + command + fileName.substring(indexLast);
        }

        int index = fileName.lastIndexOf("."); // Якщо command відсутній ([command].txt)

        if (index == -1) {
            // Якщо немає command
            return parentPath + "/" + fileName + "[" + command + "]";
        }

        // Додаємо [command]
        return parentPath + "/" + fileName.substring(0, index) + "[" + command + "]" + fileName.substring(index);
    }
}
