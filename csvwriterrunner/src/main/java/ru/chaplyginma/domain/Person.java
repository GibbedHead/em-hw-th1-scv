package ru.chaplyginma.domain;

import ru.chaplyginma.csvwriter.annotation.CSVField;

import java.util.List;

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
        int[] nums
) {

}
