package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Optional;

public class ViewGUI extends JFrame {
    private final JButton encrypt;
    private final JButton decrypt;
    private final JButton bruteForce;

    public ViewGUI() {
        super("CaesarCipher");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalGlue());

        this.encrypt = new JButton("ENCRYPT");
        addButton(encrypt, panel);

        this.decrypt = new JButton("DECRYPT");
        addButton(decrypt, panel);

        this.bruteForce = new JButton("BRUTE FORCE");
        addButton(bruteForce, panel);

        getContentPane().add(panel);

        setPreferredSize(new Dimension(260, 220));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addButton(JButton button, JPanel panel) {
        button.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(button);
        panel.add(Box.createVerticalGlue());
    }

    public Optional<String> getFilePath() {
        JFileChooser fileChooser = new JFileChooser();
        while (true) {
            int ret = fileChooser.showDialog(null, "Open File");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if (selectedFile != null && selectedFile.exists()) {
                    return Optional.of(selectedFile.getAbsolutePath());
                }
            } else {
                return Optional.empty();
            }
        }
    }

    public void clickButtonEncrypt(ActionListener actionListener) {
        encrypt.addActionListener(actionListener);
    }

    public void clickButtonDecrypt(ActionListener actionListener) {
        decrypt.addActionListener(actionListener);
    }

    public void clickButtonBruteForce(ActionListener actionListener) {
        bruteForce.addActionListener(actionListener);
    }
}
