package ru.chaplyginma.csvwriter.escaper;

import ru.chaplyginma.csvwriter.schema.CSVSchema;

public class CSVEscaper {

    private final CSVSchema schema;

    public CSVEscaper(CSVSchema schema) {
        this.schema = schema;
    }

    public String escape(String string) {
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
