package ru.chaplyginma;

import ru.chaplyginma.csvwriter.writer.CSVWriter;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CSVWriter writer = new CSVWriter();

        writer.write(List.of("Aaaa", "Bbbb", "Cccc"));
    }
}