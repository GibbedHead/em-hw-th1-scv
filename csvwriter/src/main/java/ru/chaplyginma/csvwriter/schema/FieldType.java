package ru.chaplyginma.csvwriter.schema;

/**
 * The {@code FieldType} enum represents the different types of fields
 * that can be processed by the {@link ColumnBuilder} for CSV serialization.
 * Each constant corresponds to a specific category of field type.
 */
public enum FieldType {
    /**
     * Represents a field that is either a primitive type or primitive wrapper or a String.
     */
    PRIMITIVE_OR_STRING,
    /**
     * Represents an array of primitive types or primitive wrapper or Strings.
     */
    PRIMITIVE_OR_STRING_ARRAY,
    /**
     * Represents a collection (e.g., List, Set) containing primitive types or primitive wrapper or Strings.
     */
    PRIMITIVE_OR_STRING_COLLECTION
}
