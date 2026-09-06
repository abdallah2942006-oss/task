import java.util.*;
import java.util.stream.*;

public class AdvancedOperations {
    static class Student {
        String name;
        String department;
        double grade;

        Student(String name, String department, double grade) {
            this.name = name;
            this.department = department;
            this.grade = grade;
        }

        public String getName() { return name; }
        public String getDepartment() { return department; }
        public double getGrade() { return grade; }

        @Override
        public String toString() {
            return name + " (" + department + ", " + grade + ")";
        }
    }

    static class Employee {
        String name;
        int age;
        String department;
        double salary;

        Employee(String name, int age, String department, double salary) {
            this.name = name;
            this.age = age;
            this.department = department;
            this.salary = salary;
        }

        public String getName() { return name; }
        public int getAge() { return age; }
        public String getDepartment() { return department; }
        public double getSalary() { return salary; }

        @Override
        public String toString() {
            return name + " (" + salary + ")";
        }
    }

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(10, 5, 3, 7, 2, 10, 5, 8, 9, 0, -3, 4);

        List<Student> students = Arrays.asList(
                new Student("Ali", "IT", 85),
                new Student("Mona", "CS", 92),
                new Student("Ahmed", "IT", 60),
                new Student("Sara", "CS", 70),
                new Student("Omar", "IS", 45),
                new Student("Laila", "IS", 78)
        );

        List<Employee> employees = Arrays.asList(
                new Employee("Ali", 30, "HR", 5000),
                new Employee("Mona", 25, "IT", 7000),
                new Employee("Ahmed", 30, "HR", 5500),
                new Employee("Sara", 27, "IT", 7200),
                new Employee("Omar", 40, "Finance", 8000),
                new Employee("Laila", 35, "Finance", 8200)
        );

        // 1. Sort employees by salary, then by name
        List<Employee> sortedEmployees = employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary)
                        .thenComparing(Employee::getName))
                .collect(Collectors.toList());
        System.out.println("Employees sorted by salary then name: " + sortedEmployees);

        // 2. Find second highest number (distinct values)
        Optional<Integer> secondHighest = numbers.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(1)
                .findFirst();
        System.out.println("Second highest: " + secondHighest.orElse(null));

        // 3. Find duplicate elements
        Set<Integer> seen = new HashSet<>();
        Set<Integer> duplicates = numbers.stream()
                .filter(n -> !seen.add(n))
                .collect(Collectors.toCollection(TreeSet::new));
        System.out.println("Duplicates: " + duplicates);

        // 4. Remove null or empty strings
        List<String> names = Arrays.asList("Ali", "", null, "Mona", "Ahmed", "", null);
        List<String> cleanedNames = names.stream()
                .filter(Objects::nonNull)
                .filter(name -> !name.isEmpty())
                .collect(Collectors.toList());
        System.out.println("Cleaned names: " + cleanedNames);

        // 5. Partition students into pass/fail (pass = grade >= 50)
        Map<Boolean, List<Student>> passFail = students.stream()
                .collect(Collectors.partitioningBy(s -> s.getGrade() >= 50));
        System.out.println("Passed: " + passFail.get(true));
        System.out.println("Failed: " + passFail.get(false));
    }
}
