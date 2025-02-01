package org.example;

import org.example.view.CLI;
import org.example.controller.Controller;
import org.example.model.FileService;
import org.example.model.cipher.CipherManager;
import org.example.view.ViewGUI;

import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

// Перевір мій код
public class Runner {

    private final Controller controller;

    public Runner(Controller controller) {
        this.controller = controller;
    }

    public static void main(String[] args) {
        FileService service = new FileService();
        CipherManager manager = new CipherManager();
        Controller controller = new Controller(service, manager);

        Runner runner = new Runner(controller);

        SwingUtilities.invokeLater(() -> {
            ViewGUI viewGUI = new ViewGUI();
            runner.processGUI(viewGUI, "GUI");
        });

//        if (args.length == 3) {
//            runner.processArgs(args);
//        } else if (args.length == 1) {
//            SwingUtilities.invokeLater(() -> {
//                ViewGUI viewGUI = new ViewGUI();
//                runner.processGUI(viewGUI, args[0]);
//            });
//        } else {
//            CLI cli = new CLI();
//            runner.processCLI(cli);
//        }
    }

    private void processArgs(String[] args) {
        String command = args[0];
        String filePath = args[1];
        int key;

        if (!isCommandValide(command)) {
            System.out.println("Enter a valid command: [ENCRYPT, DECRYPT, BRUTE_FORCE]");
            return;
        }

        if (!isFileExists(filePath)) {
            System.out.println("Invalid file path");
            return;
        }

        try {
            key = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.out.println("Enter a number");
            return;
        }

        controller.run(command, filePath, key);
    }

    private void processCLI(CLI cli) {
        while (true) {
            if (cli.getMessage().equalsIgnoreCase("EXIT")) {
                cli.close();
                return;
            }

            String command = cli.getCommand();
            while (!isCommandValide(command)) {
                cli.printMessage("Enter a valid command: [ENCRYPT, DECRYPT, BRUTE_FORCE]");
                command = cli.getCommand();
            }

            String filePath = cli.getFilePath();
            while (!isFileExists(filePath)) {
                cli.printMessage("Invalid file path");
                command = cli.getCommand();
            }

            int key = 0;
            if (!command.equals("BRUTE_FORCE")) {
                key = cli.getKey();
            }

            controller.run(command, filePath, key);
        }
    }

    private void processGUI(ViewGUI viewGUI, String gui) {
        if (!isCommandValideGui(gui)) {
            System.out.println("Enter a valid command: [GUI]");
            return;
        }

        viewGUI.clickButtonEncrypt(e -> processCommand("ENCRYPT", viewGUI, 1));

        viewGUI.clickButtonDecrypt(e -> processCommand("DECRYPT", viewGUI, 1));

        viewGUI.clickButtonBruteForce(e -> processCommand("BRUTE_FORCE", viewGUI, 0));
    }

    private void processCommand(String command, ViewGUI viewGUI, int key) {
        Optional<String> filePath = viewGUI.getFilePath();
        if (filePath.isEmpty()) {
            System.out.println("Invalid file selected. Please try again.");
            return;
        }
        controller.run(command, filePath.get(), key);
    }

    private boolean isCommandValide(String command) {
        if (command == null || command.isEmpty()) {
            return false;
        }
        return command.equals("ENCRYPT") || command.equals("DECRYPT") || command.equals("BRUTE_FORCE");
    }

    private boolean isCommandValideGui(String command) {
        if (command == null || command.isEmpty()) {
            return false;
        }
        return command.equals("GUI");
    }

    private boolean isFileExists(String pathFile) {
        if (pathFile == null || pathFile.isEmpty()) {
            return false;
        }

        Path path = Paths.get(pathFile);
        return Files.exists(path) && Files.isRegularFile(path);
    }
}
