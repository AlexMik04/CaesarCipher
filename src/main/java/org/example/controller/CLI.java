package org.example.controller;

import org.example.model.CaesarCipher;
import org.example.model.FileService;

import java.nio.file.Path;

public class CLI {

    private final FileService service;
    private final CaesarCipher cipher;

    public CLI(FileService service, CaesarCipher cipher) {
        this.service = service;
        this.cipher = cipher;
    }

    public void run(String filePath, String command, int key) {
        switch (command) {
            case "ENCRYPT" -> {
                String line = service.readInFromFile(filePath);
                String encrypt = cipher.encrypt(line, key);
                String file = getModifiedFilePath(filePath, "ENCRYPT");
                service.writeOutToFile(encrypt, file);
            }
            case "DECRYPT" -> {
                String line = service.readInFromFile(filePath);
                String decrypt = cipher.decrypt(line, key);
                String file = getModifiedFilePath(filePath, "DECRYPT");
                service.writeOutToFile(decrypt, file);
            }
            case "BRUTE_FORCE" -> {
                String line = service.readInFromFile(filePath);
                String bruteForce = cipher.bruteForce(line);
                String file = getModifiedFilePath(filePath, "BRUTE_FORCE");
                service.writeOutToFile(bruteForce, file);
            }
            default -> System.out.println("Enter a valid command: [ENCRYPT, DECRYPT, BRUTE_FORCE]");
        }
    }

    private String getModifiedFilePath(String filePath, String command) {
        Path path = Path.of(filePath);
        String parentPath = path.getParent().toString(); // Шлях до папки
        String fileName = path.getFileName().toString(); // Ім'я файлу з розширенням

        if (fileName.contains("[ENCRYPT]") || fileName.contains("[DECRYPT]") || fileName.contains("[BRUTE_FORCE]")) {
            int indexFirst = fileName.lastIndexOf("[") + 1;
            int indexLast = fileName.lastIndexOf("]");

            return parentPath + "/" + fileName.substring(0, indexFirst) + command + fileName.substring(indexLast);
        }

        int index = fileName.lastIndexOf("."); // Пошук останньої крапки для відділення розширення

        if (index == -1) {
            // Якщо немає розширення
            return parentPath + "/" + fileName + "[" + command + "]";
        }

        // Додаємо [command] перед розширенням
        return parentPath + "/" + fileName.substring(0, index) + "[" + command + "]" + fileName.substring(index);
    }
}
