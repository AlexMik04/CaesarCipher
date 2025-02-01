package org.example.model.constants;

import java.util.List;

public final class Frequencies {
    public static final List<Double> FREQUENCIES_ALPHABET_ENGLISH = List.of(
//                        A       B       C       D       E       F
            0.0804, 0.0154, 0.0306, 0.0399, 0.1251, 0.0230,
//              G       H       I       J       K       L       M
            0.0196, 0.0549, 0.0726, 0.0016, 0.0067, 0.0414, 0.0253,
//              N       O       P       Q       R       S       T
            0.0709, 0.0760, 0.0200, 0.0011, 0.0612, 0.0654, 0.0925,
//              U       V       W       X       Y       Z
            0.0271, 0.0099, 0.0192, 0.0019, 0.0173, 0.0009
    );

    public static final List<Double> FREQUENCIES_ALPHABET_UKRAINIAN = List.of(
//                       А      Б      В      Г      Ґ       Д      Е
            0.064, 0.013, 0.046, 0.013, 0.0001, 0.027, 0.042,
//             Є      Ж      З      И      І      Ї      Й      К      Л
            0.005, 0.007, 0.020, 0.055, 0.044, 0.010, 0.009, 0.033, 0.027,
//             М      Н      О      П      Р      С      Т      У      Ф
            0.029, 0.068, 0.086, 0.025, 0.043, 0.037, 0.045, 0.027, 0.003,
//             Х      Ц      Ч      Ш      Щ      Ь      Ю      Я
            0.011, 0.010, 0.011, 0.005, 0.004, 0.016, 0.008, 0.019
    );

    public static final List<Double> FREQUENCIES_SPECIAL_SYMBOLS = List.of(
            0.13, 0.11, 0.05, 0.05, 0.07, 0.07, 0.04, 0.03, 0.04, 0.4
    );

    private Frequencies() {}
}
