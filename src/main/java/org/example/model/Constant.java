package org.example.model;

import java.util.List;

public class Constant {
    public static final List<String> ALPHABET_ENGLAND_UPPER = List.of(
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"
    );

    public static final List<String> ALPHABET_ENGLAND_LOWER = List.of(
            "a", "b", "v", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
            "w", "x", "y", "z"
    );

    public static final List<String> ALPHABET_UKRAINIAN_UPPER = List.of(
            "А", "Б", "В", "Г", "Ґ", "Д", "Е", "Є", "Ж", "З",
            "И", "І", "Ї", "Й", "К", "Л", "М", "Н", "О", "П", "Р", "С",
            "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш", "Щ", "Ь", "Ю", "Я"
    );

    public static final List<String> ALPHABET_UKRAINIAN_LOWER = List.of(
            "а", "б", "в", "г", "ґ", "д", "е", "є", "ж", "з",
            "и", "і", "ї", "й", "к", "л", "м", "н", "о", "п", "р", "с",
            "т", "у", "ф", "х", "ц", "ч", "ш", "щ", "ь", "ю", "я"
    );

    public static final List<String> NON_ALPHABET_SYMBOLS = List.of(
            ".", ",", "«", "»", "'", "\"", ":", "!", "?", " "
    );

    public static final List<String> PATTERN_REGEX = List.of(
            "[.,!,?]\\s[A-Z]", "[\\,,;]\\s[a-z]", "\\s[—]\\s", "[.,!,?]$",
            "^[A-Z]", "^[0-9]", "^[АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯ]",
            "[.,!,?]\\s[АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯ]"
    );

    private Constant() {}
}