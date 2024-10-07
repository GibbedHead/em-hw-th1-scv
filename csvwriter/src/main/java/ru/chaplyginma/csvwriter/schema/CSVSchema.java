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

    public CSVSchema(
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

    public CSVSchema(Class<?> sourceObjectClass) {
        this(
                sourceObjectClass,
                DEFAULT_USE_HEADER,
                DEFAULT_SEPARATOR,
                DEFAULT_COLLECTION_SEPARATOR,
                DEFAULT_QUOTE_CHARACTER
        );
    }

    public CSVSchema(Class<?> sourceObjectClass, boolean useHeader) {
        this(
                sourceObjectClass,
                useHeader,
                DEFAULT_SEPARATOR,
                DEFAULT_COLLECTION_SEPARATOR,
                DEFAULT_QUOTE_CHARACTER
        );
    }

    public CSVSchema(Class<?> sourceObjectClass, boolean useHeader, String separator) {
        this(
                sourceObjectClass,
                useHeader,
                separator,
                DEFAULT_COLLECTION_SEPARATOR,
                DEFAULT_QUOTE_CHARACTER
        );
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
}
