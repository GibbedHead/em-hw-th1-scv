package ru.chaplyginma.csvwriter.schema;

import java.lang.reflect.Field;

public class Column {

    private final String name;
    private final FieldType type;
    private final Field field;

    public Column(String name, FieldType type, Field field) {
        this.name = name;
        this.type = type;
        this.field = field;
    }

    public String getName() {
        return name;
    }

    public FieldType getType() {
        return type;
    }

    public Field getField() {
        return field;
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", field=" + field +
                '}';
    }
}
