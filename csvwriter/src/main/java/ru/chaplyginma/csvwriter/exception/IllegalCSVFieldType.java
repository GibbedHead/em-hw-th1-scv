package ru.chaplyginma.csvwriter.exception;

public class IllegalCSVFieldType extends IllegalArgumentException {
    public IllegalCSVFieldType(String message) {
        super(message);
    }
}
