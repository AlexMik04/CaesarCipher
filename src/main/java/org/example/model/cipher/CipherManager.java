package org.example.model.cipher;

import org.example.model.constants.Language;
import org.example.model.SymbolsData;
import org.example.model.constants.Frequencies;
import org.example.model.constants.Symbols;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CipherManager {
    private final SymbolsData symbolsEngland;
    private final SymbolsData symbolsUkrainian;

    private final CaesarCipher caesarCipher;

    public CipherManager() {
        this.caesarCipher = new CaesarCipher();

        this.symbolsEngland = new SymbolsData(
                Language.ENGLAND,
                Symbols.ALPHABET_ENGLISH,
                Frequencies.FREQUENCIES_ALPHABET_ENGLISH,
                Symbols.SYMBOLS_SPECIAL,
                Frequencies.FREQUENCIES_SPECIAL_SYMBOLS);

        this.symbolsUkrainian = new SymbolsData(
                Language.UKRAINIAN,
                Symbols.ALPHABET_UKRAINIAN,
                Frequencies.FREQUENCIES_ALPHABET_UKRAINIAN,
                Symbols.SYMBOLS_SPECIAL,
                Frequencies.FREQUENCIES_SPECIAL_SYMBOLS);
    }

    private SymbolsData findSymbolsData(String text) {
        for (String symbol : text.toUpperCase().split("")) {
            if (Symbols.ALPHABET_UKRAINIAN.contains(symbol)) {
                return symbolsUkrainian;
            }
        }
        return symbolsEngland;
    }

    private Map<String, Double> calculateTextSymbolsFrequencies(String text) {
        Map<String, Integer> countMap = new HashMap<>();
        int totalSymbols = 0;

        for (String symbol : text.toUpperCase().split("")) {
            countMap.put(symbol, countMap.getOrDefault(symbol, 0) + 1);
            totalSymbols++;
        }

        if (totalSymbols == 0) {
            return Collections.emptyMap(); // Якщо немає літер, повертаємо пусту мапу
        }

        Map<String, Double> frequencyMap = new HashMap<>();
        for (String symbol : countMap.keySet()) {
            frequencyMap.put(symbol, countMap.getOrDefault(symbol, 0) / (double) totalSymbols);
        }

        return frequencyMap;
    }

    private int findShift(Map<String, Double> textFrequency, SymbolsData symbolsData) {
        int bestShift = 0;
        double minDiff = Double.MAX_VALUE;
        int length = symbolsData.getAlphabetSize();

        for (int shift = 0; shift < length; shift++) { // Перебираємо всі можливі зсуви
            double totalDiff = 0.0;

            for (int index = 0; index < length; index++) {
                String originalSymbol = symbolsData.getAlphabetSymbol(index).orElse("");
                String shiftedSymbol = symbolsData.getAlphabetSymbol((index + shift) % length).orElse(""); // Зсув у межах алфавіту

                double textFreq = textFrequency.getOrDefault(originalSymbol, 0.0);
                double langFreq = symbolsData.getSymbolFrequency(shiftedSymbol);

                totalDiff += Math.abs(textFreq - langFreq);
            }

            if (totalDiff < minDiff) {
                minDiff = totalDiff;
                bestShift = shift;
            }
        }

        return bestShift;
    }

    public String bruteForce(String text) {
        SymbolsData symbolsData = findSymbolsData(text);
        Map<String, Double> textFrequency = calculateTextSymbolsFrequencies(text);
        int shift = findShift(textFrequency, symbolsData);
        return caesarCipher.encrypt(text, symbolsData, shift);
    }

    public String decrypt(String text, int shift) {
        return encrypt(text, -shift);
    }

    public String encrypt(String text, int shift) {
        SymbolsData symbolsData = findSymbolsData(text);
        return caesarCipher.encrypt(text, symbolsData, shift);
    }
}
