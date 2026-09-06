import java.util.*;

public class NumericStreamsAndReductions {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(10, 5, 3, 7, 2, 10, 5, 8, 9, 0, -3, 4);
        List<Double> doubles = Arrays.asList(10.5, 20.0, 15.5, 30.0);

        // 1. Sum using reduce
        int sum = numbers.stream()
                .reduce(0, Integer::sum);
        System.out.println("Sum: " + sum);

        // 2. Maximum and minimum
        Optional<Integer> max = numbers.stream().max(Integer::compareTo);
        Optional<Integer> min = numbers.stream().min(Integer::compareTo);
        System.out.println("Max: " + max.orElse(null));
        System.out.println("Min: " + min.orElse(null));

        // 3. Average of doubles
        double average = doubles.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
        System.out.println("Average: " + average);

        // 4. Multiply all integers using reduce
        int product = numbers.stream()
                .reduce(1, (a, b) -> a * b);
        System.out.println("Product: " + product);

        // 5. Count positive numbers
        long positiveCount = numbers.stream()
                .filter(n -> n > 0)
                .count();
        System.out.println("Positive count: " + positiveCount);
    }
}
