package org.example.model.cipher;

import org.example.model.alphabet.AlphabetCountry;
import org.example.model.alphabet.AlphabetManager;


import java.util.*;

public abstract class CipherManager {

    protected final AlphabetManager alphabetManager;

    CipherManager(AlphabetManager alphabetManager) {
        if (alphabetManager == null) {
            throw new IllegalArgumentException("AlphabetManager is null");
        }

        this.alphabetManager = alphabetManager;
    }

    public Map<String, Double> calculateTextSymbolsFrequencies(String text) {
        if (text == null || text.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, Integer> countMap = new HashMap<>();
        int totalSymbols = 0;

        for (String symbol : text.toUpperCase().split("")) {
            countMap.put(symbol, countMap.getOrDefault(symbol, 0) + 1);
            totalSymbols++;
        }

        if (totalSymbols == 0) {
            return Collections.emptyMap();
        }

        Map<String, Double> frequencyMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            frequencyMap.put(entry.getKey(), entry.getValue() / (double) totalSymbols);
        }

        return frequencyMap;
    }

    public Optional<AlphabetCountry> findAlphabetData(String symbol) {
        if (symbol == null || symbol.isEmpty()) {
            return Optional.empty();
        }

        return alphabetManager.getAlphabets().stream()
                .filter(alphabetData -> alphabetData.containsAlphabetSymbol(symbol.toUpperCase()))
                .findFirst();
    }

    public Map<AlphabetCountry, Integer> findCaesarKeyByAlphabet(String text) {
        if (text == null || text.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<AlphabetCountry, StringBuilder> textByAlphabet = new HashMap<>();
        for (String symbol : text.split("")) {
            findAlphabetData(symbol).ifPresent(alphabetData -> textByAlphabet
                    .computeIfAbsent(alphabetData, k -> new StringBuilder())
                    .append(symbol));
        }

        Map<AlphabetCountry, Integer> keysByAlphabet = new HashMap<>();
        for (Map.Entry<AlphabetCountry, StringBuilder> entry : textByAlphabet.entrySet()) {
            AlphabetCountry alphabetCountry = entry.getKey();
            String filteredText = entry.getValue().toString();
            int caesarKey = findCaesarKeyForAlphabet(filteredText, alphabetCountry);
            keysByAlphabet.put(alphabetCountry, caesarKey);
        }

        return keysByAlphabet;
    }

    private int findCaesarKeyForAlphabet(String text, AlphabetCountry alphabetCountry) {
        int bestCaesarKey = 0;
        double minDiff = Double.MAX_VALUE;
        int lengthAlphabet = alphabetCountry.getAlphabetSymbolsSize();

        Map<String, Double> mapTextSymbolsFrequencies = calculateTextSymbolsFrequencies(text);

        for (int key = 0; key < lengthAlphabet; key++) {
            double totalDiff = 0.0;

            for (int index = 0; index < lengthAlphabet; index++) {
                String originalSymbol = alphabetCountry.getAlphabetSymbol(index).orElse(null);
                String keySymbol = alphabetCountry.getAlphabetSymbol((index + key) % lengthAlphabet).orElse(null);

                if (originalSymbol == null || keySymbol == null) {
                    continue;
                }

                double textFreq = mapTextSymbolsFrequencies.getOrDefault(originalSymbol, 0.0);
                double langFreq = alphabetCountry.getAlphabetSymbolFrequency(keySymbol);

                totalDiff += Math.abs(textFreq - langFreq);
            }

            if (totalDiff < minDiff) {
                minDiff = totalDiff;
                bestCaesarKey = key;
            }
        }

        return bestCaesarKey;
    }


    public abstract String decrypt(String text, int caesarKey);
    public abstract String encrypt(String text, int caesarKey);
    public abstract String bruteForce(String text, Map<AlphabetCountry, Integer> mapAlphabetKey);

}
