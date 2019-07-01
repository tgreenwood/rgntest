package com.epam.rgntest;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public final class DictionaryUtils {

    public static final int MAX_LENGTH_TERM = 50;
    public static final int MAX_LENGTH_DEFINITION = 250;
    public static final int MIN_LENGTH = 1;

    private DictionaryUtils() {
    }
    
    public static String generateTerm() {
        return randomAlphabetic(MIN_LENGTH, MAX_LENGTH_TERM);
    }

    public static String generateDefinition() {
        return randomAlphabetic(1, MAX_LENGTH_DEFINITION);
    }

}
