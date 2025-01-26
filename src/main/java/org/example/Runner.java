package org.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Runner {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Create a request of the following type: java -jar myApp.jar <command> <pathFile> <key>");
            return;
        }

        String command = args[0];
        String pathFile = args[1];
        int key;

        try {
            key = Integer.parseInt(args[2]);
        } catch (IllegalArgumentException e) {
            System.out.println("Enter a number");
            return;
        }

        if (!isCommand(command)) {
            System.out.println("Enter a valid command: [ENCRYPT, DECRYPT, BRUTE_FORCE]");
            return;
        }

        if (!isFileExists(pathFile)) {
            System.out.println("Invalid file path");
            return;
        }

        FileService service = new FileService();
        CaesarCipher cipher = new CaesarCipher();
        CLI cli = new CLI(service, cipher);
        cli.run(pathFile, command, key);
    }

    private static boolean isCommand(String command) {
        return command.equals("ENCRYPT") || command.equals("DECRYPT") || command.equals("BRUTE_FORCE");
    }

    private static boolean isFileExists(String pathFile) {
        if (pathFile == null || pathFile.isEmpty()) {
            return false;
        }

        Path path = Paths.get(pathFile);
        return Files.exists(path) && Files.isRegularFile(path);
    }
}
