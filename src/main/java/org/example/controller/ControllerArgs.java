package org.example.controller;

import org.example.model.constants.Command;

public class ControllerArgs {
    private final ControllerManager controllerManager;

    public ControllerArgs(ControllerManager controllerManager) {
        if (controllerManager == null) {
            throw new IllegalArgumentException("ControllerManager is null");
        }

        this.controllerManager = controllerManager;
    }

    public void processRun(String[] args) {
        if (args.length < 2 || args.length > 3) {
            return;
        }

        String command = args[0].toUpperCase();
        String filePath = args[1];

        Command cmd;
        try {
            cmd = Command.valueOf(command);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid command: " + command);
            return;
        }

        if (controllerManager.isFileNotExists(filePath)) {
            System.out.println("Invalid file path: " + filePath);
            return;
        }

        if ((cmd == Command.ENCRYPT || cmd == Command.DECRYPT) && args.length < 3) {
            System.out.println(
                    "For the command [ENCRYPT or DECRYPT], the key is required.\n" +
                            "Please provide the key in the following format: " +
                            "java -jar <path_to_jar>/myApp.jar <command> <file_path> <key>"
            );
            return;
        }

        int key = 0;
        if (args.length == 3) {
            try {
                key = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                System.out.println("Key must be a number.");
                return;
            }
        }
        controllerManager.run(cmd, filePath, key);
    }
}
