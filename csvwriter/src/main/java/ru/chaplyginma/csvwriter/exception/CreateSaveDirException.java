package ru.chaplyginma.csvwriter.exception;

import java.io.IOException;

public class CreateSaveDirException extends IOException {
    public CreateSaveDirException(String message) {
        super(message);
    }
}
