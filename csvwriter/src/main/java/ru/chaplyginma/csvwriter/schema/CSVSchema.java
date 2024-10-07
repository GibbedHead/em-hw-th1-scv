package ru.chaplyginma.csvwriter.schema;

import ru.chaplyginma.csvwriter.annotation.CSVField;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.Set;

public class CSVSchema {

    private static final boolean DEFAULT_USE_HEADER = true;
    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_COLLECTION_SEPARATOR = ';';

    private final char separator;
    private final char collectionSeparator;
    private final boolean useHeader;

    private final Class<?> sourceObjectClass;
    private final LinkedHashSet<Column> columns = new LinkedHashSet<>();

    public CSVSchema(Class<?> sourceObjectClass, boolean useHeader, char separator, char collectionSeparator) {
        this.separator = separator;
        this.collectionSeparator = collectionSeparator;
        this.useHeader = useHeader;
        this.sourceObjectClass = sourceObjectClass;
        makeSchema();
    }

    public CSVSchema(Class<?> sourceObjectClass) {
        this(
                sourceObjectClass,
                DEFAULT_USE_HEADER,
                DEFAULT_SEPARATOR,
                DEFAULT_COLLECTION_SEPARATOR
        );
    }

    public CSVSchema(Class<?> sourceObjectClass, boolean useHeader) {
        this(
                sourceObjectClass,
                useHeader,
                DEFAULT_SEPARATOR,
                DEFAULT_COLLECTION_SEPARATOR
        );
    }

    public CSVSchema(Class<?> sourceObjectClass, boolean useHeader, char separator) {
        this(
                sourceObjectClass,
                useHeader,
                separator,
                DEFAULT_COLLECTION_SEPARATOR
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

    public char getSeparator() {
        return separator;
    }

    public char getCollectionSeparator() {
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
}
