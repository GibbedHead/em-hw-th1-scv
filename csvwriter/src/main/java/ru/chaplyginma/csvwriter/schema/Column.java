package ru.chaplyginma.csvwriter.schema;

import java.lang.reflect.Field;

public record Column(String name, FieldType type, Field field) {
}
