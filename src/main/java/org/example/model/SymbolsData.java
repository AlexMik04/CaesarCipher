package org.example.model;

import org.example.model.constants.Language;

import java.util.*;

public class SymbolsData {
    private final String languageSymbols;

    private final List<String> alphabet;
    private final List<String> nonAlphabetSymbols;

    private final Map<String, Integer> mapAllSymbolsToIndex;
    private final Map<String, Double> mapAllSymbolsFrequencies;

    public SymbolsData(Language languageSymbols, List<String> alphabet, List<Double> alphabetFrequencies,
                       List<String> nonAlphabetSymbols, List<Double> nonAlphabetFrequencies) {

        this.languageSymbols = languageSymbols.name();

        this.alphabet = List.copyOf(alphabet);
        this.nonAlphabetSymbols = List.copyOf(nonAlphabetSymbols);

        this.mapAllSymbolsToIndex = new HashMap<>();
        this.mapAllSymbolsFrequencies = new HashMap<>();

        if (alphabet.size() != alphabetFrequencies.size()) {
            throw new IllegalArgumentException("The number of letters and their frequencies must match");
        }
        if (nonAlphabetSymbols.size() != nonAlphabetFrequencies.size()) {
            throw new IllegalArgumentException("The number of non-letter symbols and their frequencies must match");
        }

        for (int index = 0; index < alphabet.size(); index++) {
            mapAllSymbolsToIndex.put(alphabet.get(index), index);
            mapAllSymbolsFrequencies.put(alphabet.get(index), alphabetFrequencies.get(index));
        }

        for (int index = 0; index < nonAlphabetSymbols.size(); index++) {
            mapAllSymbolsToIndex.put(nonAlphabetSymbols.get(index), index);
            mapAllSymbolsFrequencies.put(nonAlphabetSymbols.get(index), nonAlphabetFrequencies.get(index));
        }
    }

    public String getLanguageSymbols() {
        return languageSymbols;
    }

    public boolean isAlphabetSymbol(String symbol) {
        return alphabet.contains(symbol);
    }

    public boolean isNonAlphabetSymbol(String symbol) {
        return nonAlphabetSymbols.contains(symbol);
    }

    public Optional<String> getAlphabetSymbol(int index) {
        return Optional.ofNullable(alphabet.get(index));
    }

    public Optional<String> getNonAlphabetSymbol(int index) {
        return Optional.ofNullable(nonAlphabetSymbols.get(index));
    }

    public OptionalInt getIndexSymbol(String letter) {
        return mapAllSymbolsToIndex.containsKey(letter)
                ? OptionalInt.of(mapAllSymbolsToIndex.get(letter))
                : OptionalInt.empty();
    }

    public Double getSymbolFrequency(String symbol) {
        return mapAllSymbolsFrequencies.getOrDefault(symbol, 0.0);
    }

    public int getNonAlphabetSize() {
        return nonAlphabetSymbols.size();
    }

    public int getAlphabetSize() {
        return alphabet.size();
    }
}
