package org.example.controller;

import org.example.model.FileService;
import org.example.model.alphabet.AlphabetCountry;
import org.example.model.cipher.CipherManager;
import org.example.view.ViewGUI;

import java.util.Map;
import java.util.Optional;

public class ControllerGUI {
    private static final String COMMAND_GUI = "GUI";

    private final FileService service;
    private final CipherManager cipherManager;

    public ControllerGUI(ControllerManager controllerManager) {
        if (controllerManager == null) {
            throw new IllegalArgumentException("ControllerManager is null");
        }

        this.service = controllerManager.service();
        this.cipherManager = controllerManager.cipherManager();
    }

    public void processRun(String command) {
        if (!COMMAND_GUI.equals(command)) {
            System.out.println("Enter a valid command: [GUI]");
            return;
        }

        ViewGUI viewGUI = new ViewGUI();
        viewGUI.clickButtonOpenFile(e -> {
            Optional<String> optionalFilePath = viewGUI.getFilePath();
            if (optionalFilePath.isEmpty()) {
                return;
            }

            String filePath = optionalFilePath.get();
            String text = service.readInFromFile(filePath);
            viewGUI.setText(text);
            viewGUI.setTextStatus("Open: " + filePath);
        });

        viewGUI.clickButtonSaveAs(e -> {
            Optional<String> optionalFilePath = viewGUI.getFilePathSave();
            if (optionalFilePath.isEmpty()) {
                return;
            }

            String filePath = optionalFilePath.get();
            String text = viewGUI.getText();
            service.writeOutToFile(text, filePath);
            viewGUI.setTextStatus("Save to: " + filePath);
        });

        viewGUI.clickButtonEncrypt(e -> {
            String text = viewGUI.getText();
            int keyCipher = viewGUI.getKeyCipher();
            String encrypt = cipherManager.encrypt(text, keyCipher);
            viewGUI.setText(encrypt);
            viewGUI.setTextStatus("Text was ENCRYPT text with key " + keyCipher);
        });

        viewGUI.clickButtonDecrypt(e -> {
            String text = viewGUI.getText();
            int keyCipher = viewGUI.getKeyCipher();
            String decrypt = cipherManager.decrypt(text, keyCipher);
            viewGUI.setText(decrypt);
            viewGUI.setTextStatus("Text was DECRYPT with key" + keyCipher);
        });

        viewGUI.clickButtonBruteForce(e -> {
            String text = viewGUI.getText();
            Map<AlphabetCountry, Integer> keysByAlphabet = cipherManager.findCaesarKeyByAlphabet(text);
            String bruteForce = cipherManager.bruteForce(text, keysByAlphabet);
            String infoStatus = getInfoStatusAlphabetKey(keysByAlphabet);
            viewGUI.setText(bruteForce);
            viewGUI.setTextStatus("Text was BRUTE FORCE with alphabet-key: " + infoStatus);
        });
    }

    private String getInfoStatusAlphabetKey(Map<AlphabetCountry, Integer> keysByAlphabet) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<AlphabetCountry, Integer> entry : keysByAlphabet.entrySet()) {
            String alphabetCountry = entry.getKey().getCountry();
            int keyCipher = entry.getValue();
            builder.append(alphabetCountry).append("-");
            builder.append(keyCipher).append(" ");
        }
        return builder.toString().trim();
    }
}
