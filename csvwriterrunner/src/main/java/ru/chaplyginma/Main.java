package ru.chaplyginma;

import ru.chaplyginma.csvwriter.schema.CSVSchema;
import ru.chaplyginma.csvwriter.writer.CSVWriter;
import ru.chaplyginma.domain.Address;
import ru.chaplyginma.domain.Person;

import java.util.List;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        List<Person> people = getPeople();

        CSVSchema schema = new CSVSchema(Person.class);

        CSVWriter writer = new CSVWriter(schema);

        writer.write(people);
    }

    private static List<Person> getPeople() {
        Person person1 = new Person(
                "FirstName1",
                "LastName1",
                1,
                new Address(1, "Street1, app.12", "City1; Zip"),
                List.of("Work11", "Work12", "Work13 \"dddd"),
                new int[]{1, 2, 3}
        );

        Person person2 = new Person(
                "FirstName2",
                "LastName2",
                2,
                new Address(2, "Street2", "City2"),
                List.of("Work21", "Work22, str.1", "Work23"),
                new int[]{4, 5, 6}
        );

        Person person3 = new Person(
                "FirstName3",
                "LastName3",
                3,
                new Address(3, "Street3", "City3"),
                List.of("Work31", "Work32; 11", "Work33"),
                new int[]{4, 5, 6}
        );

        return List.of(person1, person2, person3);
    }
}