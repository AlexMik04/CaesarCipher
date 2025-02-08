package org.example.view;

import java.util.Scanner;

public class CLI {
    private final Scanner scanner;

    public CLI() {
        this.scanner = new Scanner(System.in);
    }

    public String getCommand() {
        return scanner.nextLine().trim().toUpperCase();
    }

    public String getFilePath() {
        return scanner.nextLine().trim();
    }

    public int getKey() {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                printMessage("Invalid key. Please enter a number: ");
            }
        }
    }

    public String getMessage() {
        return scanner.nextLine().trim();
    }

    public void printMessage(String message) {
        System.out.print(message);
    }

    public void close() {
        scanner.close();
    }
}
