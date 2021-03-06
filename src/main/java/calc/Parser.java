package calc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parser {
    private static final String DEFAULT_DELIMITER = ",|:";
    private static final String CUSTOM_DELIMITER_BEGIN = "//";
    private static final String CUSTOM_DELIMITER_END = "\n";

    public static List<Integer> run(String input) {
        return Optional.ofNullable(input).map(String::trim)
                                        .filter(x -> x.length() > 0)
                                        .map(x -> validate(tokenize(x)))
                                        .orElse(Arrays.asList(0));
    }

    private static Stream<String> tokenize(String input) {
        String delimiter = DEFAULT_DELIMITER;
        if (input.startsWith(CUSTOM_DELIMITER_BEGIN)) {
            delimiter += "|\\" + input.substring(
                    CUSTOM_DELIMITER_BEGIN.length(),
                    input.indexOf(CUSTOM_DELIMITER_END)
            );
            input = input.substring(input.indexOf(CUSTOM_DELIMITER_END) + 1);
        }
        return Stream.of(input.split(delimiter));
    }

    private static List<Integer> validate(Stream<String> tokens) {
        try {
            final List<Integer> numbers = tokens.map(Integer::parseInt)
                                                .collect(Collectors.toList());
            if (numbers.stream().anyMatch(x -> x < 0)) {
                throw new RuntimeException();
            }
            return numbers;
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }
}