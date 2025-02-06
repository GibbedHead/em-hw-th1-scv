package ru.chaplyginma.csvwriter.file;

import ru.chaplyginma.csvwriter.exception.CreateSaveDirException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The {@code CSVFileWriter} class is responsible for writing CSV data to a specified file.
 * It provides methods to create directories if they do not exist and to write the CSV content
 * to a file.
 */
public class CSVFileWriter {

    /**
     * Writes the given CSV content to a file at the specified path.
     *
     * @param path the path of the file where the CSV content will be written
     * @param csv  the CSV content to be written to the file
     * @throws IOException            if an I/O error occurs during writing
     * @throws CreateSaveDirException if unable to create the parent directory for the specified file
     */
    public void writeCsv(String path, String csv) throws IOException, CreateSaveDirException {
        File file = new File(path);

        if (!file.getParentFile().mkdirs() && !file.getParentFile().exists()) {
            throw new CreateSaveDirException(String.format("Unable to create directory `%s`", file.getParent()));
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(csv);
        }
    }
}
