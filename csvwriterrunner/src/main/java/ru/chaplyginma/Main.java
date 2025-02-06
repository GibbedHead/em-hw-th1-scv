package ru.chaplyginma;

import ru.chaplyginma.csvwriter.exception.CSVWriterException;
import ru.chaplyginma.csvwriter.writer.CSVWriter;
import ru.chaplyginma.domain.Address;
import ru.chaplyginma.domain.Person;

import java.util.List;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        List<Person> people = getPeople();

        try {
            CSVWriter csvWriter = CSVWriter.forCollection(people);
            csvWriter.write("csv/people.csv");
        } catch (CSVWriterException e) {
            Logger log = Logger.getLogger(Main.class.getName());
            log.severe(e.getMessage());
        }
    }

    private static List<Person> getPeople() {
        Person person1 = new Person(
                "FirstName1",
//                "LastName1",
                null,
                1,
                new Address(1, "Street1, app.12", "City1; Zip"),
                List.of("Work11", "Work12", "Work13 \"dddd"),
                new int[]{1, 2, 3},
                new Double[]{1.0, 2.0, null}
        );

        Person person2 = new Person(
                "FirstName2",
                "LastName2",
                2,
                new Address(2, "Street2", "City2"),
                List.of("Work21", "Work22, str.1", "Work23"),
                new int[]{4, 5, 6},
                new Double[]{4.0, 5.0, 6.0}
        );

        Person person3 = new Person(
                "FirstName3",
                "LastName3",
                3,
                new Address(3, "Street3", "City3"),
                List.of("Work31", "Work32; 11", "Work33"),
                new int[]{4, 5, 6},
                new Double[]{null, 8.0, 9.0}
        );

        return List.of(person1, person2, person3);
    }
}