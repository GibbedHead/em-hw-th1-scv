package ru.chaplyginma.csvwriter.writer;

import ru.chaplyginma.csvwriter.file.CSVFileWriter;
import ru.chaplyginma.csvwriter.generator.CSVGenerator;

import java.io.IOException;
import java.util.Collection;

/**
 * The {@code CSVWriter} class is responsible for generating a CSV-formatted string
 * from a collection of objects and writing that string to a specified file.
 *
 * @param <T> the type of objects contained in the collection
 */
public class CSVWriter<T> {

    private final CSVGenerator<T> csvGenerator;
    private final CSVFileWriter fileWriter;

    public CSVWriter(CSVGenerator<T> csvGenerator, CSVFileWriter fileWriter) {
        this.csvGenerator = csvGenerator;
        this.fileWriter = fileWriter;
    }

    /**
     * Writes the CSV representation of the given collection to a file at the specified path.
     *
     * @param collection the collection of objects to be converted to CSV format
     * @param path       the path of the file where the CSV content will be written
     * @throws IOException            if an I/O error occurs during writing to the file
     * @throws IllegalAccessException if an attempt to access a field fails while generating CSV
     */
    public void write(Collection<T> collection, String path) throws IOException, IllegalAccessException {
        String csv = csvGenerator.getCSV(collection);

        fileWriter.writeCsv(path, csv);
    }

}
