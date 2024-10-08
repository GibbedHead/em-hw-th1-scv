package ru.chaplyginma.csvwriter.writer;

import ru.chaplyginma.csvwriter.exception.CreateSaveDirException;
import ru.chaplyginma.csvwriter.generator.CSVGenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

public class CSVWriter<T> {

    private final CSVGenerator<T> csvGenerator;

    public CSVWriter(CSVGenerator<T> csvGenerator) {
        this.csvGenerator = csvGenerator;
    }

    private static void writeCsv(String path, String csv) throws IOException {
        File file = new File(path);

        if (!file.getParentFile().mkdirs() && !file.getParentFile().exists()) {
            throw new CreateSaveDirException(String.format("Unable to create directory `%s`", file.getParent()));
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(csv);
        }
    }

    public void write(Collection<T> collection, String path) throws IOException, IllegalAccessException {
        String csv = csvGenerator.getCSV(collection);

        writeCsv(path, csv);
    }

}
