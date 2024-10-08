package ru.chaplyginma.csvwriter.generator;

import ru.chaplyginma.csvwriter.escaper.CSVEscaper;
import ru.chaplyginma.csvwriter.exception.IllegalCSVFieldType;
import ru.chaplyginma.csvwriter.schema.CSVSchema;
import ru.chaplyginma.csvwriter.schema.Column;
import ru.chaplyginma.csvwriter.schema.FieldType;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

public class CSVGenerator<T> {

    private final CSVSchema schema;
    private final CSVEscaper escaper;

    public CSVGenerator(CSVSchema schema, CSVEscaper escaper) {
        this.schema = schema;
        this.escaper = escaper;
    }

    public String getCSV(Collection<T> collection) throws IllegalAccessException {
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
        if (column.field().get(object) == null) {
            return schema.getNullValueString();
        }
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
        return escaper.escape(builder.toString());
    }

    private String getCollectionString(Column column, Object object) throws IllegalAccessException {
        if (column.field().get(object) == null) {
            return schema.getNullValueString();
        }
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
        return escaper.escape(builder.toString());
    }

    private String getPrimitiveString(Column column, Object object) throws IllegalAccessException {
        Object value = column.field().get(object);
        return value == null ? schema.getNullValueString() : escaper.escape(String.valueOf(value));
    }
}
