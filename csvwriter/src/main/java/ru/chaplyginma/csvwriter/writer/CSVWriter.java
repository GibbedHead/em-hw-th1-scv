package ru.chaplyginma.csvwriter.writer;

import ru.chaplyginma.csvwriter.schema.CSVSchema;
import ru.chaplyginma.csvwriter.schema.Column;
import ru.chaplyginma.csvwriter.schema.FieldType;

import java.util.Collection;

public class CSVWriter {

    private final CSVSchema schema;

    public CSVWriter(CSVSchema schema) {
        this.schema = schema;
    }

    public void write(Collection<?> collection) throws IllegalAccessException {
        StringBuilder builder = new StringBuilder();

        if (schema.isUseHeader()) {
            for (Column column : schema.getColumns()) {
                builder.append(column.getName()).append(schema.getSeparator());
            }
            builder.append('\n');
        }

        for (Object object : collection) {
            builder.append(getRowString(object));
        }
        System.out.println(builder);
    }

    private String getRowString(Object object) throws IllegalAccessException {
        StringBuilder row = new StringBuilder();
        for (Column column : schema.getColumns()) {
            row.append(getColumnString(column, object)).append(schema.getSeparator());
        }
        row.append("\n");

        return row.toString();
    }

    private String getColumnString(Column column, Object object) throws IllegalAccessException {
        if (column.getType() == FieldType.PRIMITIVE) {
            return String.valueOf(column.getField().get(object));
        }
        if (column.getType() == FieldType.STRING) {
            return (String) column.getField().get(object);
        }
        if (column.getType() == FieldType.PRIMITIVE_WRAPPER) {
            return String.valueOf(column.getField().get(object));
        }
        return "";
    }
}
