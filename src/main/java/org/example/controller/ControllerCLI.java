package org.example.controller;

import org.example.model.constants.Command;
import org.example.view.CLI;

public class ControllerCLI {
    private static final String COMMAND_EXIT = "Y";

    private final ControllerManager controllerManager;
    private final CLI cli;

    public ControllerCLI(ControllerManager controllerManager, CLI cli) {
        if (controllerManager == null) {
            throw new IllegalArgumentException("ControllerManager is null");
        } else if (cli == null) {
            throw new IllegalArgumentException("CLI is null");
        }

        this.controllerManager = controllerManager;
        this.cli = cli;
    }

    public void processRun() {
        while (true) {
            cli.printMessage("Exit the program? [Y/N]: ");

            if (COMMAND_EXIT.equalsIgnoreCase(cli.getMessage())) {
                cli.close();
                return;
            }

            Command cmd = null;
            while (cmd == null) {
                cli.printMessage("Enter a valid command [ENCRYPT, DECRYPT, BRUTE_FORCE]: ");
                try {
                    cmd = Command.valueOf(cli.getCommand().toUpperCase());
                } catch (IllegalArgumentException e) {
                    cli.printMessage("Invalid command.");
                }
            }

            cli.printMessage("Enter file path [<path_directory>/<file>]: ");

            String filePath = cli.getFilePath();
            while (controllerManager.isFileNotExists(filePath)) {
                cli.printMessage(
                        "Invalid file path: " + filePath + "\n" +
                        "Enter valid file path [<path_directory>/<file>]: ");
                filePath = cli.getFilePath();
            }

            if (cmd != Command.BRUTE_FORCE) {
                cli.printMessage("Enter key number: ");
            }

            int key = (cmd == Command.BRUTE_FORCE) ? 0 : cli.getKey();
            controllerManager.run(cmd, filePath, key);
        }
    }
}
