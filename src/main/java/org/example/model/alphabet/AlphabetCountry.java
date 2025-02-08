package org.example.model.alphabet;

import java.util.*;

public class AlphabetCountry {
    private final String country;

    private final List<String> alphabet;

    private final Map<String, Integer> mapAlphabetToIndex;
    private final Map<String, Double> mapAlphabetFrequencies;

    public AlphabetCountry(
            String country, List<String> alphabet, List<Double> alphabetFrequencies
    ) {

        if (country == null || country.isEmpty()) {
            throw new IllegalArgumentException("Name country is null or empty");
        } else if (alphabet.isEmpty()) {
            throw new IllegalArgumentException("List Alphabet is empty");
        } else if (alphabetFrequencies.isEmpty()) {
            throw new IllegalArgumentException("List Alphabet Frequencies is empty");
        } else if (alphabet.size() != alphabetFrequencies.size()) {
            throw new IllegalArgumentException("The number of letters and their frequencies must match");
        }

        this.country = country;

        this.alphabet = List.copyOf(alphabet);

        this.mapAlphabetToIndex = new HashMap<>();
        this.mapAlphabetFrequencies = new HashMap<>();

        for (int index = 0; index < alphabet.size(); index++) {
            mapAlphabetToIndex.put(alphabet.get(index), index);
            mapAlphabetFrequencies.put(alphabet.get(index), alphabetFrequencies.get(index));
        }
    }

    public String getCountry() {
        return country;
    }

    private boolean isValidSymbol(String symbol) {
        return symbol != null && !symbol.isEmpty();
    }

    public boolean containsAlphabetSymbol(String symbol) {
        return isValidSymbol(symbol) && mapAlphabetToIndex.containsKey(symbol);
    }

    public Optional<String> getAlphabetSymbol(int index) {
        return (index >= 0 && index < alphabet.size())
                ? Optional.of(alphabet.get(index))
                : Optional.empty();
    }

    public OptionalInt getIndexAlphabetSymbol(String symbol) {
        return mapAlphabetToIndex.containsKey(symbol)
                ? OptionalInt.of(mapAlphabetToIndex.get(symbol))
                : OptionalInt.empty();
    }

    public Double getAlphabetSymbolFrequency(String symbol) {
        return mapAlphabetFrequencies.getOrDefault(symbol, 0.0);
    }

    public int getAlphabetSymbolsSize() {
        return alphabet.size();
    }
}
