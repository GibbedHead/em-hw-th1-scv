package ru.chaplyginma;

import ru.chaplyginma.csvwriter.escaper.CSVEscaper;
import ru.chaplyginma.csvwriter.exception.CreateSaveDirException;
import ru.chaplyginma.csvwriter.exception.FieldValueAccessException;
import ru.chaplyginma.csvwriter.file.CSVFileWriter;
import ru.chaplyginma.csvwriter.generator.CSVGenerator;
import ru.chaplyginma.csvwriter.schema.CSVSchema;
import ru.chaplyginma.csvwriter.writer.CSVWriter;
import ru.chaplyginma.domain.Address;
import ru.chaplyginma.domain.Person;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, CreateSaveDirException, FieldValueAccessException {
        CSVSchema schema = CSVSchema.forClass(Person.class)
                .build();

        CSVEscaper csvEscaper = new CSVEscaper(schema);
        CSVGenerator<Person> generator = new CSVGenerator<>(schema, csvEscaper);
        CSVFileWriter fileWriter = new CSVFileWriter();

        CSVWriter<Person> writer = new CSVWriter<>(generator, fileWriter);

        List<Person> people = getPeople();
        writer.write(people, "csv/people.csv");
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