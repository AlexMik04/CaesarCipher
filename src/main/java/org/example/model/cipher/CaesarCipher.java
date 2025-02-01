package org.example.model.cipher;

import org.example.model.SymbolsData;

import java.util.Optional;
import java.util.OptionalInt;

class CaesarCipher {

    private void validateInput(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Text cannot be null or empty");
        }
    }

    String encrypt(String text, SymbolsData symbolsData, int shift) {
        validateInput(text);

        StringBuilder builder = new StringBuilder();
        for (String symb : text.split("")) {
            builder.append(symbolEncryption(symb, symbolsData, shift));
        }
        return builder.toString();
    }

    private String symbolEncryption(String symbol, SymbolsData symbolsData, int shift) {
        validateInput(symbol);

        if (!symbolsData.isAlphabetSymbol(symbol) && !symbolsData.isNonAlphabetSymbol(symbol)) {
            return symbol; // Якщо символ не знайдено в жодному алфавіті, повертаємо його без змін
        }

        OptionalInt optionalIndex = symbolsData.getIndexSymbol(symbol);
        if (optionalIndex.isEmpty()) {
            return symbol;
        }
        int index = optionalIndex.getAsInt();

        if (symbolsData.isAlphabetSymbol(symbol)) {
            return getEncodeAlphabetSymbol(symbolsData, index, shift).orElse(symbol);
        } else {
            return getEncodeNonAlphabetSymbol(symbolsData, index, shift).orElse(symbol);
        }
    }

    private Optional<String> getEncodeAlphabetSymbol(SymbolsData symbolsData, int index, int shift) {
        int length = symbolsData.getAlphabetSize();
        int newIndex = getEncodeNewIndex(index, length, shift);
        return symbolsData.getAlphabetSymbol(newIndex); // Повертаємо символ з алфавіту
    }

    private Optional<String> getEncodeNonAlphabetSymbol(SymbolsData symbolsData, int index, int shift) {
        int length = symbolsData.getNonAlphabetSize();
        int newIndex = getEncodeNewIndex(index, length, shift);
        return symbolsData.getNonAlphabetSymbol(newIndex); // Повертаємо символ з неалфавітних
    }

    private int getEncodeNewIndex(int index, int length, int shift) {
        int newIndex = (index + shift) % length;
        return (newIndex < 0) ? newIndex + length : newIndex; // Корекція для від’ємних зміщень
    }
}
