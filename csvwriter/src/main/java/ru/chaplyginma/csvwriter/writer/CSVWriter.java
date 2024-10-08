package ru.chaplyginma.csvwriter.writer;

import ru.chaplyginma.csvwriter.exception.CreateSaveDirException;
import ru.chaplyginma.csvwriter.exception.IllegalCSVFieldType;
import ru.chaplyginma.csvwriter.schema.CSVSchema;
import ru.chaplyginma.csvwriter.schema.Column;
import ru.chaplyginma.csvwriter.schema.FieldType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

public class CSVWriter<T> {

    private final CSVSchema schema;

    public CSVWriter(CSVSchema schema) {
        this.schema = schema;
    }

    private static void writeCsv(String path, String csv) throws IOException {
        File file = new File(path);

        if (!file.getParentFile().mkdirs() && !file.getParentFile().exists()) {
            throw new CreateSaveDirException(String.format("Unable to create directory `%s`", file.getParent()));
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(csv);
        }
    }

    public void write(Collection<T> collection, String path) throws IOException, IllegalAccessException {
        String csv = getCSV(collection);

        writeCsv(path, csv);
    }

    private String getCSV(Collection<T> collection) throws IllegalAccessException {
        StringBuilder builder = new StringBuilder();

        if (schema.isUseHeader()) {
            Iterator<Column> columnIterator = schema.getColumns().iterator();
            while (columnIterator.hasNext()) {
                Column column = columnIterator.next();
                builder.append(column.name());
                if (columnIterator.hasNext()) {
                    builder.append(",");
                }
            }
            builder.append('\n');
        }

        for (Object object : collection) {
            builder.append(getRowString(object));
        }
        return builder.toString();
    }

    private String getRowString(Object object) throws IllegalAccessException {
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

    private String getColumnString(Column column, Object object) throws IllegalAccessException {
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

    private String getArrayString(Column column, Object object) throws IllegalAccessException {
        StringBuilder builder = new StringBuilder();
        int length = Array.getLength(column.field().get(object));
        for (int i = 0; i < length; i++) {
            Object element = Array.get(column.field().get(object), i);
            String elementString = element == null ? schema.getNullValueString() : String.valueOf(element);
            builder.append(elementString);

            if (i < length - 1) {
                builder.append(schema.getCollectionSeparator());
            }
        }
        return escape(builder.toString());
    }

    private String getCollectionString(Column column, Object object) throws IllegalAccessException {
        StringBuilder builder = new StringBuilder();
        Iterator<?> iterator = ((Collection<?>) column.field().get(object)).iterator();
        while (iterator.hasNext()) {
            Object element = iterator.next();
            String elementString = element == null ? schema.getNullValueString() : String.valueOf(element);
            builder.append(elementString);
            if (iterator.hasNext()) {
                builder.append(schema.getCollectionSeparator());
            }
        }
        return escape(builder.toString());
    }

    private String getPrimitiveString(Column column, Object object) throws IllegalAccessException {
        Object value = column.field().get(object);
        return value == null ? schema.getNullValueString() : escape(String.valueOf(value));
    }


    private String escape(String string) {
        String quoteChar = schema.getQuoteCharacter();
        if (quoteChar.equals(string)) {
            return String.format("%s%s%s%s", quoteChar, quoteChar, quoteChar, quoteChar);
        }
        String escapedString = string.replace(quoteChar, String.format("%s%s", quoteChar, quoteChar));
        if (escapedString.contains(schema.getSeparator()) || escapedString.contains(schema.getCollectionSeparator())) {
            escapedString = String.format("%s%s%s", quoteChar, escapedString, quoteChar);
        }
        return escapedString;
    }
}
