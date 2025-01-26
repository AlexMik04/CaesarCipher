package org.example;

import java.util.Map;
import java.util.HashMap;

public class CaesarCipher {
    
    private final String[] upperCaseLettersEng;
    private final String[] lowerCaseLettersEng;

    private final String[] upperCaseLettersUkr;
    private final String[] lowerCaseLettersUkr;

    private final String[] nonLetterSymbols;

    private final Map<String, Integer> symbolsIndexMap;
    private final Map<Integer, String> bruteForceKeySymbolsMap;

    public CaesarCipher() {
        this.upperCaseLettersEng = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
        this.lowerCaseLettersEng = "abcdefghijklmnopqrstuvwxyz".split("");
        this.upperCaseLettersUkr = "АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯ".split("");
        this.lowerCaseLettersUkr = "абвгґдеєжзиіїйклмнопрстуфхцчшщьюя".split("");
        this.nonLetterSymbols = ".,«»'\"\\:!? ".split("");

        this.symbolsIndexMap = new HashMap<>();
        this.bruteForceKeySymbolsMap = new HashMap<>();

        initializeMapCharIndex(symbolsIndexMap, upperCaseLettersEng);
        initializeMapCharIndex(symbolsIndexMap, lowerCaseLettersEng);

        initializeMapCharIndex(symbolsIndexMap, upperCaseLettersUkr);
        initializeMapCharIndex(symbolsIndexMap, lowerCaseLettersUkr);

        initializeMapCharIndex(symbolsIndexMap, nonLetterSymbols);
    }

    private void initializeMapCharIndex(Map<String, Integer> map, String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            map.put(strings[i], i);
        }
    }

    public int getKeyBruteForceDecrypt() {
        return 0;
    }

    public String bruteForce(String line) {
        StringBuilder builder = new StringBuilder();
        for (int key = 1; key <= symbolsIndexMap.size(); key++) {
            String decryptSymbols = encrypt(line, -key);
            builder.append(decryptSymbols).append(System.lineSeparator());
            bruteForceKeySymbolsMap.put(key, decryptSymbols);
        }
        return builder.toString();
    }

    public String decrypt(String line, int key) {
        return encrypt(line, -key);
    }

    public String encrypt(String line, int key) {
        StringBuilder builder = new StringBuilder();
        for (String symbol : line.split("")) {
            if (symbol.matches("[A-Z]")) {
                builder.append(symbolEncryption(symbol, upperCaseLettersEng, key));
            } else if (symbol.matches("[a-z]")) {
                builder.append(symbolEncryption(symbol, lowerCaseLettersEng, key));
            } else if (symbol.matches("[АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯ]")) {
                builder.append(symbolEncryption(symbol, upperCaseLettersUkr, key));
            } else if (symbol.matches("[абвгґдеєжзиіїйклмнопрстуфхцчшщьюя]")) {
                builder.append(symbolEncryption(symbol, lowerCaseLettersUkr, key));
            } else {
                builder.append(symbolEncryption(symbol, nonLetterSymbols, key));
            }
        }
        return builder.toString();
    }

    private String symbolEncryption(String symbol, String[] strings, int key) {
        if (symbolsIndexMap.containsKey(symbol)) {
            int index = symbolsIndexMap.get(symbol);
            int newIndex = (index + key) % strings.length;
            if (newIndex < 0) { // для від'ємних зміщень
                newIndex += strings.length;
            }
            return strings[newIndex];
        }
        return symbol; // Якщо символ не знайдено, повертаємо його без змін
    }
}
