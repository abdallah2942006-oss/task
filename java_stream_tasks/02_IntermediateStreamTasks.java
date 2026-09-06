import java.util.*;
import java.util.stream.*;

public class IntermediateStreamTasks {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(10, 5, 3, 7, 2, 10, 5, 8, 9, 0, -3, 4);
        List<String> names = Arrays.asList("Ali", "Mona", "Ahmed", "Sara", "Amr", "Laila", "Kareem", "Nada", "Nour", "Samy", "", null);

        // 1. Count strings longer than 5 characters
        long countLongNames = names.stream()
                .filter(Objects::nonNull)
                .filter(name -> name.length() > 5)
                .count();
        System.out.println("Strings longer than 5: " + countLongNames);

        // 2. Find first element matching a condition
        Optional<Integer> firstGreaterThan7 = numbers.stream()
                .filter(n -> n > 7)
                .findFirst();
        System.out.println("First number > 7: " + firstGreaterThan7.orElse(null));

        // 3. Check if any number is divisible by 5
        boolean anyDivisibleBy5 = numbers.stream()
                .anyMatch(n -> n % 5 == 0);
        System.out.println("Any divisible by 5: " + anyDivisibleBy5);

        // 4. Collect elements into a Set
        Set<Integer> numberSet = numbers.stream()
                .collect(Collectors.toSet());
        System.out.println("Set: " + numberSet);

        // 5. Skip first 3 elements
        List<Integer> afterFirst3 = numbers.stream()
                .skip(3)
                .collect(Collectors.toList());
        System.out.println("After skipping first 3: " + afterFirst3);
    }
}
