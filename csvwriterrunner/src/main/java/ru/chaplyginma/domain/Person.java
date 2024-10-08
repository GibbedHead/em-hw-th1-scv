package ru.chaplyginma.domain;

import ru.chaplyginma.csvwriter.annotation.CSVField;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public record Person(
        @CSVField(name = "First Name")
        String firstName,
        @CSVField(name = "Last Name")
        String lastName,
        @CSVField(name = "Age")
        int age,
        Address address,
        @CSVField(name = "Work Places")
        List<String> workPlaces,
        @CSVField(name = "Nums")
        int[] nums,
        @CSVField(name = "Doubles")
        Double[] doubles
) {
        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof Person person)) return false;
            return age == person.age && Objects.deepEquals(nums, person.nums) && Objects.equals(lastName, person.lastName) && Objects.equals(address, person.address) && Objects.equals(firstName, person.firstName) && Objects.deepEquals(doubles, person.doubles) && Objects.equals(workPlaces, person.workPlaces);
        }

        @Override
        public int hashCode() {
                return Objects.hash(firstName, lastName, age, address, workPlaces, Arrays.hashCode(nums), Arrays.hashCode(doubles));
        }

        @Override
        public String toString() {
                return "Person{" +
                        "firstName='" + firstName + '\'' +
                        ", lastName='" + lastName + '\'' +
                        ", age=" + age +
                        ", address=" + address +
                        ", workPlaces=" + workPlaces +
                        ", nums=" + Arrays.toString(nums) +
                        ", doubles=" + Arrays.toString(doubles) +
                        '}';
        }
}
