package ru.chaplyginma.csvwriter.schema;

import ru.chaplyginma.csvwriter.annotation.CSVField;
import ru.chaplyginma.csvwriter.exception.IllegalCSVFieldType;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The {@code CSVSchema} class defines the structure of a CSV file, including
 * configurations such as separators, quote characters, and whether to use headers.
 * It also provides functionality to build a schema based on the fields of a
 * specified source object class annotated with {@code CSVField}.
 */

public class CSVSchema {

    private static final boolean DEFAULT_USE_HEADER = true;
    private static final String DEFAULT_SEPARATOR = ",";
    private static final String DEFAULT_COLLECTION_SEPARATOR = ";";
    private static final String DEFAULT_QUOTE_CHARACTER = "\"";
    private static final String DEFAULT_NULL_VALUE_STRING = "";

    private final boolean useHeader;
    private final String separator;
    private final String collectionSeparator;
    private final String quoteCharacter;
    private final String nullValueString;

    private final Class<?> sourceObjectClass;
    private final LinkedHashSet<Column> columns = new LinkedHashSet<>();

    private CSVSchema(
            Class<?> sourceObjectClass,
            boolean useHeader,
            String separator,
            String collectionSeparator,
            String quoteCharacter,
            String nullValueString
    ) throws IllegalCSVFieldType {
        this.separator = separator;
        this.collectionSeparator = collectionSeparator;
        this.useHeader = useHeader;
        this.sourceObjectClass = sourceObjectClass;
        this.quoteCharacter = quoteCharacter;
        this.nullValueString = nullValueString;
        makeSchema();
    }

    /**
     * Returns a builder instance for constructing a {@code CSVSchema}.
     *
     * @param sourceObjectClass the class of the source object for which
     *                          the schema is being built.
     * @return a new instance of {@code CSVSchemaBuilder}.
     */
    public static CSVSchemaBuilder forClass(Class<?> sourceObjectClass) {
        return new CSVSchemaBuilder(sourceObjectClass);
    }

    private void makeSchema() throws IllegalCSVFieldType {
        Field[] fields = sourceObjectClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(CSVField.class)) {
                columns.add(ColumnBuilder.buildColumn(field));
            }
        }
    }

    public String getSeparator() {
        return separator;
    }

    public String getCollectionSeparator() {
        return collectionSeparator;
    }

    public boolean isUseHeader() {
        return useHeader;
    }

    public Class<?> getSourceObjectClass() {
        return sourceObjectClass;
    }

    public Set<Column> getColumns() {
        return columns;
    }

    public String getQuoteCharacter() {
        return quoteCharacter;
    }

    public String getNullValueString() {
        return nullValueString;
    }

    /**
     * The {@code CSVSchemaBuilder} is a builder class for constructing
     * instances of {@code CSVSchema}. It allows for a fluent interface
     * to set various properties of the schema before building it.
     */
    public static class CSVSchemaBuilder {
        private final Class<?> sourceObjectClass;
        private boolean useHeader = DEFAULT_USE_HEADER;
        private String separator = DEFAULT_SEPARATOR;
        private String collectionSeparator = DEFAULT_COLLECTION_SEPARATOR;
        private String quoteCharacter = DEFAULT_QUOTE_CHARACTER;
        private String nullValueString = DEFAULT_NULL_VALUE_STRING;

        public CSVSchemaBuilder(Class<?> sourceObjectClass) {
            this.sourceObjectClass = sourceObjectClass;
        }

        public CSVSchemaBuilder useHeader(boolean useHeader) {
            this.useHeader = useHeader;
            return this;
        }

        public CSVSchemaBuilder separator(String separator) {
            this.separator = separator;
            return this;
        }

        public CSVSchemaBuilder collectionSeparator(String collectionSeparator) {
            this.collectionSeparator = collectionSeparator;
            return this;
        }

        public CSVSchemaBuilder quoteCharacter(String quoteCharacter) {
            this.quoteCharacter = quoteCharacter;
            return this;
        }

        public CSVSchemaBuilder nullValueString(String nullValueString) {
            this.nullValueString = nullValueString;
            return this;
        }

        public CSVSchema build() {
            return new CSVSchema(
                    sourceObjectClass,
                    useHeader,
                    separator,
                    collectionSeparator,
                    quoteCharacter,
                    nullValueString
            );
        }
    }
}