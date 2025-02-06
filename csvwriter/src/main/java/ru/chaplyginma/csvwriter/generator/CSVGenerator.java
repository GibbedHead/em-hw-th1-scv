package ru.chaplyginma.csvwriter.generator;

import ru.chaplyginma.csvwriter.escaper.CSVEscaper;
import ru.chaplyginma.csvwriter.exception.FieldValueAccessException;
import ru.chaplyginma.csvwriter.exception.IllegalCSVFieldType;
import ru.chaplyginma.csvwriter.schema.CSVSchema;
import ru.chaplyginma.csvwriter.schema.Column;
import ru.chaplyginma.csvwriter.schema.FieldType;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

/**
 * The {@code CSVGenerator} class is responsible for generating CSV-formatted strings
 * from a collection of objects based on a provided schema. It utilizes a schema to
 * define the structure of the CSV and an escaper utility to handle special characters.
 */
public class CSVGenerator {

    private final CSVSchema schema;
    private final CSVEscaper escaper;

    public CSVGenerator(CSVSchema schema, CSVEscaper escaper) {
        this.schema = schema;
        this.escaper = escaper;
    }

    /**
     * Generates a CSV-formatted string from the provided collection of objects.
     *
     * @param collection the collection of objects to be converted to CSV format
     * @return a string representing the CSV data
     * @throws FieldValueAccessException if an attempt to access a field fails
     * @throws IllegalCSVFieldType       if the field's type is not supported for CSV serialization
     */
    public String getCSV(Collection<?> collection) throws FieldValueAccessException, IllegalCSVFieldType {
        StringBuilder builder = new StringBuilder();

        if (schema.isUseHeader()) {
            appendHeader(builder);
        }

        for (Object object : collection) {
            builder.append(getRowString(object));
        }
        return builder.toString();
    }

    private void appendHeader(StringBuilder builder) {
        Iterator<Column> columnIterator = schema.getColumns().iterator();
        while (columnIterator.hasNext()) {
            Column column = columnIterator.next();
            builder.append(column.name());
            if (columnIterator.hasNext()) {
                builder.append(schema.getSeparator());
            }
        }
        builder.append('\n');
    }

    private String getRowString(Object object) throws FieldValueAccessException, IllegalCSVFieldType {
        StringBuilder row = new StringBuilder();

        Iterator<Column> columnIterator = schema.getColumns().iterator();
        while (columnIterator.hasNext()) {
            Column column = columnIterator.next();
            String escapedColumnString = getColumnString(column, object);
            row.append(escapedColumnString);
            if (columnIterator.hasNext()) {
                row.append(schema.getSeparator());
            }
        }

        row.append("\n");

        return row.toString();
    }

    private String getColumnString(Column column, Object object) throws FieldValueAccessException, IllegalCSVFieldType {
        if (column.type() == FieldType.PRIMITIVE_OR_STRING) {
            return getPrimitiveString(column, object);
        }
        if (column.type() == FieldType.PRIMITIVE_OR_STRING_COLLECTION) {
            return getCollectionString(column, object);
        }
        if (column.type() == FieldType.PRIMITIVE_OR_STRING_ARRAY) {
            return getArrayString(column, object);
        }
        throw new IllegalCSVFieldType("Unsupported field type: " + column.type());
    }

    private String getArrayString(Column column, Object object) throws FieldValueAccessException {
        Object fieldValue = getFieldValue(column, object);
        if (fieldValue == null) {
            return schema.getNullValueString();
        }
        StringBuilder builder = new StringBuilder();
        int length = Array.getLength(fieldValue);
        for (int i = 0; i < length; i++) {
            Object element = Array.get(fieldValue, i);
            String elementString = element == null ? schema.getNullValueString() : String.valueOf(element);
            builder.append(elementString);

            if (i < length - 1) {
                builder.append(schema.getCollectionSeparator());
            }
        }
        return escaper.escape(builder.toString());
    }

    private String getCollectionString(Column column, Object object) throws FieldValueAccessException {
        Object fieldValue = getFieldValue(column, object);
        if (fieldValue == null) {
            return schema.getNullValueString();
        }
        StringBuilder builder = new StringBuilder();
        Iterator<?> iterator = ((Collection<?>) fieldValue).iterator();
        while (iterator.hasNext()) {
            Object element = iterator.next();
            String elementString = element == null ? schema.getNullValueString() : String.valueOf(element);
            builder.append(elementString);
            if (iterator.hasNext()) {
                builder.append(schema.getCollectionSeparator());
            }
        }
        return escaper.escape(builder.toString());
    }

    private String getPrimitiveString(Column column, Object object) throws FieldValueAccessException {
        Object fieldValue = getFieldValue(column, object);
        return fieldValue == null ? schema.getNullValueString() : escaper.escape(String.valueOf(fieldValue));
    }

    private static Object getFieldValue(Column column, Object object) throws FieldValueAccessException {
        Object fieldValue;
        try {
            fieldValue = column.field().get(object);
        } catch (IllegalAccessException e) {
            throw new FieldValueAccessException("Can't read field value for: %s".formatted(column.name()));
        }
        return fieldValue;
    }
}
