package ru.chaplyginma.csvwriter.writer;

import ru.chaplyginma.csvwriter.schema.CSVSchema;
import ru.chaplyginma.csvwriter.schema.Column;
import ru.chaplyginma.csvwriter.schema.FieldType;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

public class CSVWriter {

    private final CSVSchema schema;

    public CSVWriter(CSVSchema schema) {
        this.schema = schema;
    }

    public void write(Collection<?> collection) throws IllegalAccessException {
        StringBuilder builder = new StringBuilder();

        if (schema.isUseHeader()) {
            for (Column column : schema.getColumns()) {
                builder.append(column.name()).append(schema.getSeparator());
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
            return escape(String.valueOf(column.field().get(object)));
        }
        if (column.type() == FieldType.PRIMITIVE_OR_STRING_COLLECTION) {
            StringBuilder builder = new StringBuilder();
            Iterator<?> iterator = ((Collection<?>) column.field().get(object)).iterator();
            while (iterator.hasNext()) {
                Object element = iterator.next();
                builder.append(escape(element.toString()));
                if (iterator.hasNext()) {
                    builder.append(schema.getCollectionSeparator());
                }
            }
            return builder.toString();
        }
        if (column.type() == FieldType.PRIMITIVE_OR_STRING_ARRAY) {
            StringBuilder builder = new StringBuilder();
            int length = Array.getLength(column.field().get(object));
            for (int i = 0; i < length; i++) {
                String elementString = escape(String.valueOf(Array.get(column.field().get(object), i)));
                builder.append(elementString);

                if (i < length - 1) {
                    builder.append(schema.getCollectionSeparator());
                }
            }
            return builder.toString();
        }
        return "";
    }

    private String escape(String string) {
        String escapedString = string.replace(schema.getQuoteCharacter(), String.format("%s%s", schema.getQuoteCharacter(), schema.getQuoteCharacter()));
        if (escapedString.contains(schema.getSeparator()) || escapedString.contains(schema.getCollectionSeparator())) {
            escapedString = String.format("%s%s%s", schema.getQuoteCharacter(), escapedString, schema.getQuoteCharacter());
        }
        return escapedString;
    }
}
