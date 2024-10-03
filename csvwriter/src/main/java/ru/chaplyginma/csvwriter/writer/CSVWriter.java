package ru.chaplyginma.csvwriter.writer;

import java.util.Collection;

public class CSVWriter {

    public void write(Collection<?> collection) {
        System.out.println("Writing CSV file. Collection size: " + collection.size());
    }
}
