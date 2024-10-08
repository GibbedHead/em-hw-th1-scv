package ru.chaplyginma.csvwriter.escaper;

import ru.chaplyginma.csvwriter.schema.CSVSchema;

/**
 * The {@code CSVEscaper} class is designed to escape special characters in strings
 * according to a specified CSV schema. It ensures that any occurrences of the
 * separator, collection separator, or quote character within a string are properly
 * escaped to maintain the integrity of the CSV format.
 */

public class CSVEscaper {

    private final CSVSchema schema;

    public CSVEscaper(CSVSchema schema) {
        this.schema = schema;
    }

    /**
     * Escapes the provided string according to the rules defined in the
     * associated CSV schema. This method replaces occurrences of the quote character
     * with an escaped version and wraps the string in quotes if it contains any
     * separator or collection separator characters.
     *
     * @param string the input string to be escaped.
     * @return the escaped string suitable for inclusion in a CSV file.
     */

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
