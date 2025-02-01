package org.example.model.constants;

import java.util.List;

public final class Symbols {
    public static final List<String> ALPHABET_ENGLISH = List.of(
            "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X", "Y", "Z"
    );

    public static final List<String> ALPHABET_UKRAINIAN = List.of(
            "А", "Б", "В", "Г", "Ґ", "Д", "Е",
            "Є", "Ж", "З", "И", "І", "Ї", "Й", "К", "Л",
            "М", "Н", "О", "П", "Р", "С", "Т", "У", "Ф",
            "Х", "Ц", "Ч", "Ш", "Щ", "Ь", "Ю", "Я"
    );

    public static final List<String> SYMBOLS_SPECIAL = List.of(
            ".", ",", "«", "»", "'", "\"", ":", "!", "?", " "
    );

    private Symbols() {}
}
