package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class ViewGUI extends JFrame {
    private final JButton buttonOpenFile;
    private final JButton buttonSaveAs;

    private final JTextArea textFromFile;

    private final JTextField textNumberKeyCipher;
    private final JButton buttonEncrypt;
    private final JButton buttonDecrypt;
    private final JButton buttonBruteForce;

    private final JLabel labelStatus;

    public ViewGUI() {
        super("Caesar Cipher");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel();
        this.buttonOpenFile = new JButton("Open File");
        this.buttonSaveAs = new JButton("Save As");
        topPanel.add(buttonOpenFile);
        topPanel.add(buttonSaveAs);
        add(topPanel);

        JPanel textPanel = new JPanel();
        this.textFromFile = new JTextArea(30, 90);
        this.textFromFile.setLineWrap(true);
        this.textFromFile.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textFromFile);
        textPanel.add(scrollPane);
        add(textPanel);

        JPanel bottomPanel = new JPanel();
        JLabel label = new JLabel("Kye:");
        this.textNumberKeyCipher = new JTextField("0");
        textNumberKeyCipher.setHorizontalAlignment(SwingConstants.CENTER);
        inputTextNumberKeyCipher(textNumberKeyCipher);
        textNumberKeyCipher.setPreferredSize(new Dimension(30, 30));
        this.buttonEncrypt = new JButton("ENCRYPT");
        this.buttonDecrypt = new JButton("DECRYPT");
        this.buttonBruteForce = new JButton("BRUTE FORCE");
        bottomPanel.add(label);
        bottomPanel.add(textNumberKeyCipher);
        bottomPanel.add(buttonEncrypt);
        bottomPanel.add(buttonDecrypt);
        bottomPanel.add(buttonBruteForce);
        add(bottomPanel);

        JPanel statusPanel = new JPanel();
        this.labelStatus = new JLabel();
        statusPanel.add(labelStatus);
        add(statusPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void inputTextNumberKeyCipher(JTextField textField) {
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent ke) {
                char ch = ke.getKeyChar();
                if (!Character.isDigit(ch) || textField.getText().length() >= 2) {
                    ke.consume();
                }
            }
        });
    }

    public Optional<String> getFilePath() {
        JFileChooser fileChooser = new JFileChooser();
        int ret = fileChooser.showOpenDialog(this);

        if (ret == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile != null && selectedFile.exists()) {
                return Optional.of(selectedFile.getAbsolutePath());
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "No file selected or file does not exist!\n" +
                                "Please select a file.",
                        "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
        return Optional.empty();
    }

    public Optional<String> getFilePathSave() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save As");

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            try {
                if (fileToSave.createNewFile() || fileToSave.exists()) {
                    return Optional.of(fileToSave.getAbsolutePath());
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Failed to create file!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error creating file: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return Optional.empty();
    }

    public String getText() {
        return textFromFile.getText();
    }

    public void setText(String text) {
        textFromFile.setText(text);
    }

    public void setTextStatus(String statusText) {
        labelStatus.setText(statusText);
    }

    public int getKeyCipher() {
        String text = textNumberKeyCipher.getText();
        return text.isEmpty() ? 0 : Integer.parseInt(text); // Перетворюємо на число
    }

    public void clickButtonOpenFile(ActionListener actionListener) {
        if (hasActionListener(buttonOpenFile)) {
            removeButtonActionListener(buttonOpenFile);
        }
        buttonOpenFile.addActionListener(actionListener);
    }

    public void clickButtonSaveAs(ActionListener actionListener) {
        if (hasActionListener(buttonSaveAs)) {
            removeButtonActionListener(buttonSaveAs);
        }
        buttonSaveAs.addActionListener(actionListener);
    }

    public void clickButtonEncrypt(ActionListener actionListener) {
        if (hasActionListener(buttonEncrypt)) {
            removeButtonActionListener(buttonEncrypt);
        }
        buttonEncrypt.addActionListener(actionListener);
    }

    public void clickButtonDecrypt(ActionListener actionListener) {
        if (hasActionListener(buttonDecrypt)) {
            removeButtonActionListener(buttonDecrypt);
        }
        buttonDecrypt.addActionListener(actionListener);
    }

    public void clickButtonBruteForce(ActionListener actionListener) {
        if (hasActionListener(buttonBruteForce)) {
            removeButtonActionListener(buttonBruteForce);
        }
        buttonBruteForce.addActionListener(actionListener);
    }

    private boolean hasActionListener(JButton button) {
        return button.getActionListeners().length > 0;
    }

    private void removeButtonActionListener(JButton button) {
        for (ActionListener al : button.getActionListeners()) {
            button.removeActionListener(al);
        }
    }
}
