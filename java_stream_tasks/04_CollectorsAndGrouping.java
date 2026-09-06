import java.util.*;
import java.util.stream.*;

public class CollectorsAndGrouping {
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
            return name + " (" + age + ", " + department + ", " + salary + ")";
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

        // 1. Group students by department
        Map<String, List<Student>> studentsByDepartment = students.stream()
                .collect(Collectors.groupingBy(Student::getDepartment));
        System.out.println("Students by department: " + studentsByDepartment);

        // 2. Partition numbers into even and odd
        Map<Boolean, List<Integer>> evenOdd = numbers.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        System.out.println("Even: " + evenOdd.get(true));
        System.out.println("Odd: " + evenOdd.get(false));

        // 3. Comma-separated string
        String joinedNames = Arrays.asList("Ali", "Mona", "Ahmed", "Sara")
                .stream()
                .collect(Collectors.joining(", "));
        System.out.println("Joined names: " + joinedNames);

        // 4. Group employees by age and count
        Map<Integer, Long> employeesByAge = employees.stream()
                .collect(Collectors.groupingBy(Employee::getAge, Collectors.counting()));
        System.out.println("Employees by age: " + employeesByAge);

        // 5. Average salary per department
        Map<String, Double> averageSalaryByDepartment = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.averagingDouble(Employee::getSalary)
                ));
        System.out.println("Average salary by department: " + averageSalaryByDepartment);
    }
}
