package ru.chaplyginma;

import ru.chaplyginma.csvwriter.schema.CSVSchema;
import ru.chaplyginma.csvwriter.writer.CSVWriter;
import ru.chaplyginma.domain.Address;
import ru.chaplyginma.domain.Person;

import java.util.List;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {

        Person person1 = new Person(
                "FirstName1",
                "LastName1",
                1,
                new Address(1, "Street1", "City1")
        );

        Person person2 = new Person(
                "FirstName2",
                "LastName2",
                2,
                new Address(2, "Street2", "City2")
        );

        Person person3 = new Person(
                "FirstName3",
                "LastName3",
                3,
                new Address(3, "Street3", "City3")
        );

        List<Person> people = List.of(person1, person2, person3);

        CSVSchema schema = new CSVSchema(Person.class);

        System.out.println(schema.getColumns());

        CSVWriter writer = new CSVWriter(schema);

        writer.write(people);
    }
}