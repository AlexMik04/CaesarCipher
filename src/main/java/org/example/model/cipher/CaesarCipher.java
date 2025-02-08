package org.example.model.cipher;

import org.example.model.alphabet.AlphabetCountry;
import org.example.model.alphabet.AlphabetManager;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

public class CaesarCipher extends CipherManager {

    public CaesarCipher(AlphabetManager alphabetManager) {
        super(alphabetManager);
    }

    @Override
    public String decrypt(String text, int caesarKey) {
        return encrypt(text, -caesarKey);
    }

    @Override
    public String encrypt(String text, int caesarKey) {
        if (text == null) {
            throw new IllegalArgumentException("Text is null");
        }

        StringBuilder builder = new StringBuilder();
        for (String symbol : text.split("")) {
            boolean found = false;
            for (AlphabetCountry alphabetCountry : alphabetManager.getAlphabets()) {
                if (alphabetCountry.containsAlphabetSymbol(symbol.toUpperCase())) {
                    builder.append(encryptPreservingCase(symbol, alphabetCountry, caesarKey));
                    found = true;
                    break;
                }
            }
            if (!found) {
                builder.append(symbol);
            }
        }
        return builder.toString();
    }

    private String encryptPreservingCase(String symbol, AlphabetCountry alphabetCountry, int caesarKey) {
        if (symbol == null) {
            throw new IllegalArgumentException("Symbol is null");
        } else if (alphabetCountry == null) {
            throw new IllegalArgumentException("Alphabet is null");
        }

        boolean isUpperCase = Character.isUpperCase(symbol.charAt(0));
        String encryptedSymbol = encryptSymbol(symbol.toUpperCase(), alphabetCountry, caesarKey);

        return isUpperCase ? encryptedSymbol : encryptedSymbol.toLowerCase();
    }

    private String encryptSymbol(String symbol, AlphabetCountry alphabetCountry, int caesarKey) {
        OptionalInt optionalIndex = alphabetCountry.getIndexAlphabetSymbol(symbol);
        if (optionalIndex.isEmpty()) {
            return symbol;
        }

        int index = optionalIndex.getAsInt();
        int length = alphabetCountry.getAlphabetSymbolsSize();

        int newIndex = calculateNewIndex(index, length, caesarKey);

        return alphabetCountry.getAlphabetSymbol(newIndex).orElse(symbol);
    }

    private int calculateNewIndex(int index, int length, int caesarKey) {
        int newIndex = (index + caesarKey) % length;
        if (newIndex < 0) {
            newIndex += length;
        }
        return newIndex;
    }


    @Override
    public String bruteForce(String text, Map<AlphabetCountry, Integer> mapAlphabetKey) {
        if (text == null) {
            throw new IllegalArgumentException("Text is null");
        } else if (mapAlphabetKey == null) {
            throw new IllegalArgumentException("Map AlphabetKey is null");
        }

        StringBuilder builder = new StringBuilder();

        for (String symbol : text.split("")) {
            Optional<AlphabetCountry> optionalAlphabetData = mapAlphabetKey.keySet().stream()
                    .filter(alphabet -> alphabet.containsAlphabetSymbol(symbol.toUpperCase()))
                    .findFirst();

            if (optionalAlphabetData.isPresent()) {
                AlphabetCountry alphabetCountry = optionalAlphabetData.get();
                int caesarKey = mapAlphabetKey.get(alphabetCountry);
                builder.append(encryptPreservingCase(symbol, alphabetCountry, caesarKey));
            } else {
                builder.append(symbol);
            }
        }

        return builder.toString();
    }

}
