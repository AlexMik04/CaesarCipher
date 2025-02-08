package org.example.controller;

import org.example.model.alphabet.AlphabetCountry;
import org.example.model.FileService;
import org.example.model.cipher.CipherManager;
import org.example.model.constants.Command;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Map;

public record ControllerManager(FileService service, CipherManager cipherManager) {

    public boolean isFileNotExists(String pathFile) {
        if (pathFile == null || pathFile.isBlank()) return true;

        try {
            Path path = Path.of(pathFile);
            return !(Files.exists(path) && Files.isRegularFile(path));
        } catch (InvalidPathException e) {
            return true;
        }
    }

    public void run(Command command, String pathFile, int key) {
        if (command == null) {
            System.out.println("Command is null");
            return;
        }

        if (isFileNotExists(pathFile)) {
            System.out.println("Invalid file path or invalid format");
            return;
        }

        String text = service.readInFromFile(pathFile);
        System.out.println("Text from the file " + pathFile + " has been successfully read");

        String newFileName = updateFileNameWithCommand(pathFile, command.name());

        switch (command) {
            case ENCRYPT -> processFile(text, newFileName, key, true);
            case DECRYPT -> processFile(text, newFileName, key, false);
            case BRUTE_FORCE -> processBruteForce(text, newFileName);
            default -> System.out.println("Error: Invalid command. Use [ENCRYPT, DECRYPT, BRUTE_FORCE].");
        }
    }

    private void processFile(String text, String newFileName, int keyCipher, boolean isEncrypt) {
        String processedText = isEncrypt
                ? cipherManager.encrypt(text, keyCipher)
                : cipherManager.decrypt(text, keyCipher);

        if (processedText.equals(text)) {
            System.out.println("Warning: " + (isEncrypt ? "Encryption" : "Decryption") + " failed, writing original text.");
        }

        service.writeOutToFile(processedText, newFileName);
        System.out.println("The file " + newFileName + " has been successfully created and written with key " + keyCipher);
    }

    private void processBruteForce(String text, String newFileName) {
        Map<AlphabetCountry, Integer> keysByAlphabet = cipherManager.findCaesarKeyByAlphabet(text);
        String bruteForcedText = cipherManager.bruteForce(text, keysByAlphabet);

        if (bruteForcedText.equals(text)) {
            System.out.println("Warning: Brute-force decryption failed, writing original text.");
        }

        for (Map.Entry<AlphabetCountry, Integer> entry : keysByAlphabet.entrySet()) {
            newFileName = addAlphabetAndKeyToFileName(newFileName, entry.getKey().getCountry(), String.valueOf(entry.getValue()));
        }

        service.writeOutToFile(bruteForcedText, newFileName);
        System.out.println("The file " + newFileName + " has been successfully created and written");
    }

    private String updateFileNameWithCommand(String filePath, String command) {
        Path path = Path.of(filePath).normalize();
        String parentPath = (path.getParent() != null) ? path.getParent().toString() : "";
        String fileName = path.getFileName().toString();

        for (Command cmd : Command.values()) {
            if (fileName.contains(cmd.name())) {
                return parentPath + "/" + fileName.replaceFirst(cmd.name(), command);
            }
        }

        int index = fileName.lastIndexOf(".");
        return parentPath + "/" + (index == -1
                ? fileName + "[" + command + "].txt"
                : fileName.substring(0, index) + "[" + command + "]" + fileName.substring(index));
    }

    private String addAlphabetAndKeyToFileName(String filePath, String alphabetCountry, String keyCipher) {
        Path path = Path.of(filePath);
        String parentPath = (path.getParent() != null) ? path.getParent().toString() : "";
        String fileName = path.getFileName().toString();

        int lastIndexPoint = fileName.lastIndexOf(".");
        String suffix = "[" + alphabetCountry + "_" + keyCipher + "]";

        return parentPath + "/" + (lastIndexPoint == -1
                ? fileName + suffix + ".txt"
                : fileName.substring(0, lastIndexPoint) + suffix + fileName.substring(lastIndexPoint));
    }
}