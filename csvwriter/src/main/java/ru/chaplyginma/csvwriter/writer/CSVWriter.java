package ru.chaplyginma.csvwriter.writer;

import ru.chaplyginma.csvwriter.file.CSVFileWriter;
import ru.chaplyginma.csvwriter.generator.CSVGenerator;

import java.io.IOException;
import java.util.Collection;

public class CSVWriter<T> {

    private final CSVGenerator<T> csvGenerator;
    private final CSVFileWriter fileWriter;

    public CSVWriter(CSVGenerator<T> csvGenerator, CSVFileWriter fileWriter) {
        this.csvGenerator = csvGenerator;
        this.fileWriter = fileWriter;
    }

    public void write(Collection<T> collection, String path) throws IOException, IllegalAccessException {
        String csv = csvGenerator.getCSV(collection);

        fileWriter.writeCsv(path, csv);
    }

}
