package ru.chaplyginma.domain;

import ru.chaplyginma.csvwriter.annotation.CSVField;

public record Person(
        @CSVField(name = "First Name")
        String firstName,
        @CSVField(name = "Last Name")
        String lastName,
        @CSVField(name = "Age")
        int age,
        Address address) {

}
