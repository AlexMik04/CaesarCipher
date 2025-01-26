package org.example.model;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class CaesarCipher {

    private final Map<String, Integer> symbolsIndexMap;
    private final Map<Integer, String> bruteForceKeySymbolsMap;

    public CaesarCipher() {
        this.symbolsIndexMap = new HashMap<>();
        this.bruteForceKeySymbolsMap = new HashMap<>();

        initializeSymbolIndexMap(symbolsIndexMap, Constant.ALPHABET_ENGLAND_UPPER);
        initializeSymbolIndexMap(symbolsIndexMap, Constant.ALPHABET_ENGLAND_LOWER);

        initializeSymbolIndexMap(symbolsIndexMap, Constant.ALPHABET_UKRAINIAN_UPPER);
        initializeSymbolIndexMap(symbolsIndexMap, Constant.ALPHABET_UKRAINIAN_LOWER);

        initializeSymbolIndexMap(symbolsIndexMap, Constant.NON_ALPHABET_SYMBOLS);
    }

    private void initializeSymbolIndexMap(Map<String, Integer> map, List<String> symbols) {
        for (int i = 0; i < symbols.size(); i++) {
            map.put(symbols.get(i), i);
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
                builder.append(symbolEncryption(symbol, Constant.ALPHABET_ENGLAND_UPPER, key));
            } else if (symbol.matches("[a-z]")) {
                builder.append(symbolEncryption(symbol, Constant.ALPHABET_ENGLAND_LOWER, key));
            } else if (symbol.matches("[АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯ]")) {
                builder.append(symbolEncryption(symbol, Constant.ALPHABET_UKRAINIAN_UPPER, key));
            } else if (symbol.matches("[абвгґдеєжзиіїйклмнопрстуфхцчшщьюя]")) {
                builder.append(symbolEncryption(symbol, Constant.ALPHABET_UKRAINIAN_LOWER, key));
            } else {
                builder.append(symbolEncryption(symbol, Constant.NON_ALPHABET_SYMBOLS, key));
            }
        }
        return builder.toString();
    }

    private String symbolEncryption(String symbol, List<String> symbols, int key) {
        if (symbolsIndexMap.containsKey(symbol)) {
            int index = symbolsIndexMap.get(symbol);
            int newIndex = (index + key) % symbols.size();
            if (newIndex < 0) { // для від'ємних зміщень
                newIndex += symbols.size();
            }
            return symbols.get(newIndex);
        }
        return symbol; // Якщо символ не знайдено, повертаємо його без змін
    }
}
