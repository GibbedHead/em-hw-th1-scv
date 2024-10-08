package ru.chaplyginma.csvwriter.file;

import ru.chaplyginma.csvwriter.exception.CreateSaveDirException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVFileWriter {

    public void writeCsv(String path, String csv) throws IOException {
        File file = new File(path);

        if (!file.getParentFile().mkdirs() && !file.getParentFile().exists()) {
            throw new CreateSaveDirException(String.format("Unable to create directory `%s`", file.getParent()));
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(csv);
        }
    }
}
