package org.example;

import org.example.controller.ControllerCLI;
import org.example.controller.ControllerArgs;
import org.example.controller.ControllerGUI;
import org.example.controller.ControllerManager;
import org.example.model.FileService;
import org.example.model.alphabet.AlphabetManager;
import org.example.model.cipher.CaesarCipher;
import org.example.model.cipher.CipherManager;
import org.example.view.CLI;

public class Runner {
    private static final String COMMAND_GUI = "GUI";
    private static final String COMMAND_CLI = "CLI";

    private final ControllerManager controllerManager;

    public Runner(ControllerManager controllerManager) {
        if (controllerManager == null) {
            throw new IllegalArgumentException("ControllerManager is null");
        }

        this.controllerManager = controllerManager;
    }

    public static void main(String[] args) {
        FileService service = new FileService();
        AlphabetManager alphabetManager = new AlphabetManager();
        CipherManager cipherManager = new CaesarCipher(alphabetManager);
        ControllerManager controllerManager = new ControllerManager(service, cipherManager);

        Runner runner = new Runner(controllerManager);

        runner.processInput(args);
    }

    private void processInput(String[] args) {
        if (args == null) {
            throw new IllegalArgumentException("args is null");
        }

        if (args.length == 3 || args.length == 2) {
            ControllerArgs controller = new ControllerArgs(controllerManager);
            controller.processRun(args);
        } else if (args.length == 1 && args[0].equalsIgnoreCase(COMMAND_GUI)) {
            ControllerGUI controller = new ControllerGUI(controllerManager);
            controller.processRun();
        } else if (args.length == 1 && args[0].equalsIgnoreCase(COMMAND_CLI)) {
            CLI cli = new CLI();
            ControllerCLI controller = new ControllerCLI(controllerManager, cli);
            controller.processRun();
        } else {
            System.out.println(
                    new StringBuilder("Enter the correct command:\n")
                            .append("java -jar <path_to_jar>/myApp.jar ENCRYPT <file_path> <key> — to encrypt a file\n")
                            .append("java -jar <path_to_jar>/myApp.jar DECRYPT <file_path> <key> — to decrypt a file\n")
                            .append("java -jar <path_to_jar>/myApp.jar BRUTE_FORCE <file_path> — to attempt brute force decryption of a file\n")
                            .append("java -jar <path_to_jar>/myApp.jar CLI — to run via the command line (CLI)\n")
                            .append("java -jar <path_to_jar>/myApp.jar GUI — to run the graphical user interface (GUI)")

            );
        }
    }
}
