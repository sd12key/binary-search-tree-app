package org.alvio.bst.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NumberUtils {
    public static List<Integer> parseNumbers(String input) throws NumberFormatException {
        String normalized = input.trim().replaceAll("\\s*,\\s*", ",");

        if (!normalized.matches("^\\d+(,\\d+)*$")) {
            throw new NumberFormatException("Comma-separated numbers expected");
        }

        return Arrays.stream(normalized.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}