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
        } else if (args.length == 1) {
            ControllerGUI controller = new ControllerGUI(controllerManager);
            controller.processRun(args[0]);
        } else if (args.length == 0) {
            CLI cli = new CLI();
            ControllerCLI controller = new ControllerCLI(controllerManager, cli);
            controller.processRun();
        }
    }
}
