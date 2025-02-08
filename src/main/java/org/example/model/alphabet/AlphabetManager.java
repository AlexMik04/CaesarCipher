package org.example.model.alphabet;

import org.example.model.constants.Country;
import org.example.model.constants.Frequencies;
import org.example.model.constants.Symbols;

import java.util.List;

public class AlphabetManager {
    private final List<AlphabetCountry> listAlphabet;

    public AlphabetManager() {
        this.listAlphabet = List.of(
                new AlphabetCountry(
                        Country.ENGLISH.name(),
                        Symbols.ALPHABET_ENGLISH,
                        Frequencies.FREQUENCIES_ALPHABET_ENGLISH
                ),
                new AlphabetCountry(
                        Country.UKRAINE.name(),
                        Symbols.ALPHABET_UKRAINIAN,
                        Frequencies.FREQUENCIES_ALPHABET_UKRAINIAN
                )
        );
    }

    public List<AlphabetCountry> getAlphabets() {
        return listAlphabet;
    }
}
