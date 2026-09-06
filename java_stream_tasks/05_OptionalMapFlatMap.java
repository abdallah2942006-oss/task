import java.util.*;
import java.util.stream.*;

public class OptionalMapFlatMap {
    public static void main(String[] args) {
        List<String> words = Arrays.asList("Java", "Stream", "API", "Lambda");

        List<List<String>> nestedWords = Arrays.asList(
                Arrays.asList("Java", "Stream"),
                Arrays.asList("API", "Lambda"),
                Arrays.asList("FlatMap", "Map")
        );

        List<Optional<String>> optionals = Arrays.asList(
                Optional.of("Ali"),
                Optional.empty(),
                Optional.of("Mona"),
                Optional.empty(),
                Optional.of("Ahmed")
        );

        List<String> names = Arrays.asList("Ali", "Mona", "Ahmed", "Sara", "Amr", "Laila");

        // 1. Flatten a list of lists
        List<String> flattened = nestedWords.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        System.out.println("Flattened: " + flattened);

        // 2. Extract all unique characters
        Set<Character> uniqueCharacters = words.stream()
                .flatMapToInt(String::chars)
                .mapToObj(c -> (char) c)
                .collect(Collectors.toCollection(TreeSet::new));
        System.out.println("Unique characters: " + uniqueCharacters);

        // 3. Filter Optionals and collect non-empty values
        List<String> nonEmptyValues = optionals.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        System.out.println("Non-empty Optionals: " + nonEmptyValues);

        // 4. Map strings to their lengths
        List<Integer> lengths = names.stream()
                .map(String::length)
                .collect(Collectors.toList());
        System.out.println("Lengths: " + lengths);

        // 5. Uppercased words starting with A
        List<String> upperA = names.stream()
                .filter(name -> name.startsWith("A"))
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println("Uppercase A words: " + upperA);
    }
}
