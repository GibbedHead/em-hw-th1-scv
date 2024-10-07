package ru.chaplyginma.csvwriter.schema;

import ru.chaplyginma.csvwriter.annotation.CSVField;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.Set;

public class CSVSchema {

    private static final boolean DEFAULT_USE_HEADER = true;
    private static final String DEFAULT_SEPARATOR = ",";
    private static final String DEFAULT_COLLECTION_SEPARATOR = ";";
    private static final String DEFAULT_QUOTE_CHARACTER = "\"";

    private final boolean useHeader;
    private final String separator;
    private final String collectionSeparator;
    private final String quoteCharacter;

    private final Class<?> sourceObjectClass;
    private final LinkedHashSet<Column> columns = new LinkedHashSet<>();

    private CSVSchema(
            Class<?> sourceObjectClass,
            boolean useHeader,
            String separator,
            String collectionSeparator,
            String quoteCharacter
    ) {
        this.separator = separator;
        this.collectionSeparator = collectionSeparator;
        this.useHeader = useHeader;
        this.sourceObjectClass = sourceObjectClass;
        this.quoteCharacter = quoteCharacter;
        makeSchema();
    }

    public static CSVSchemaBuilder forClass(Class<?> sourceObjectClass) {
        return new CSVSchemaBuilder(sourceObjectClass);
    }

    private void makeSchema() {
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

    public static class CSVSchemaBuilder {
        private final Class<?> sourceObjectClass;
        private boolean useHeader = DEFAULT_USE_HEADER;
        private String separator = DEFAULT_SEPARATOR;
        private String collectionSeparator = DEFAULT_COLLECTION_SEPARATOR;
        private String quoteCharacter = DEFAULT_QUOTE_CHARACTER;

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

        public CSVSchema build() {
            return new CSVSchema(
                    sourceObjectClass,
                    useHeader,
                    separator,
                    collectionSeparator,
                    quoteCharacter
            );
        }
    }
}