package org.example.view;

import java.util.Scanner;

public class CLI {
    private final Scanner scanner;

    public CLI() {
        this.scanner = new Scanner(System.in);
    }

    public String getCommand() {
        printMessage("Enter command (ENCRYPT, DECRYPT, BRUTE_FORCE, EXIT): ");
        return scanner.nextLine().trim().toUpperCase();
    }

    public String getFilePath() {
        printMessage("Enter file path: ");
        return scanner.nextLine().trim();
    }

    public int getKey() {
        while (true) {
            printMessage("Enter key number: ");
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                printMessage("Invalid key. Please enter a number.");
            }
        }
    }

    public String getMessage() {
        return scanner.nextLine().trim();
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void close() {
        scanner.close();
    }
}
