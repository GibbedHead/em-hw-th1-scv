package ru.chaplyginma.csvwriter.writer;

import ru.chaplyginma.csvwriter.escaper.CSVEscaper;
import ru.chaplyginma.csvwriter.exception.CSVWriterException;
import ru.chaplyginma.csvwriter.file.CSVFileWriter;
import ru.chaplyginma.csvwriter.generator.CSVGenerator;
import ru.chaplyginma.csvwriter.schema.CSVSchema;

import java.util.Collection;

/**
 * The {@code CSVWriter} class is responsible for generating a CSV-formatted string
 * from a collection of objects and writing that string to a specified file.
 */
public class CSVWriter {

    private final Collection<?> collection;
    private final CSVGenerator generator;
    private final CSVFileWriter fileWriter;

    private CSVWriter(Collection<?> collection, CSVGenerator generator, CSVFileWriter fileWriter) throws CSVWriterException {
        if (collection == null || collection.isEmpty()) {
            throw new CSVWriterException("Collection must not be null or empty");
        }

        this.collection = collection;
        this.generator = generator;
        this.fileWriter = fileWriter;
    }

    /**
     * Creates a new {@code CSVWriter} instance for the specified collection.
     *
     * @param collection the collection of objects to be converted to CSV format
     * @return a {@code CSVWriter} instance
     * @throws CSVWriterException if the provided collection is null or empty,
     *                             or if schema generation fails
     */
    public static CSVWriter forCollection(Collection<?> collection) throws CSVWriterException {
        if (collection == null || collection.isEmpty()) {
            throw new CSVWriterException("Collection must not be null or empty");
        }

        Class<?> elementClass = collection.iterator().next().getClass();

        CSVSchema schema = CSVSchema.forClass(elementClass)
                .build();

        CSVEscaper csvEscaper = new CSVEscaper(schema);
        CSVGenerator generator = new CSVGenerator(schema, csvEscaper);

        CSVFileWriter fileWriter = new CSVFileWriter();

        return new CSVWriter(collection, generator, fileWriter);
    }

    /**
     * Creates a test instance of the {@code CSVWriter} for unit testing purposes.
     *
     * @param collection the collection of objects to be converted to CSV format
     * @param generator the generator responsible for creating the CSV formatted string
     * @param fileWriter the file writer responsible for saving the CSV to a file
     * @return a test instance of {@code CSVWriter}
     * @throws CSVWriterException if the provided collection is null or empty
     */
    static CSVWriter testInstance(Collection<?> collection, CSVGenerator generator, CSVFileWriter fileWriter) throws CSVWriterException {
        return new CSVWriter(collection, generator, fileWriter);
    }

    /**
     * Writes the CSV representation of the given collection to a file at the specified path.
     *
     * @param path the path of the file where the CSV content will be written
     * @throws CSVWriterException if creating or writing the CSV representation fails
     */
    public void write(String path) throws CSVWriterException {
        try {
            String csv = generator.getCSV(collection);
            fileWriter.writeCsv(path, csv);
        } catch (Exception e) {
            throw new CSVWriterException(e.getMessage());
        }


    }

}
