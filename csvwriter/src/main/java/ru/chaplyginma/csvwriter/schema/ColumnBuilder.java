package ru.chaplyginma.csvwriter.schema;

import ru.chaplyginma.csvwriter.annotation.CSVField;
import ru.chaplyginma.csvwriter.exception.IllegalCSVFieldType;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@code ColumnBuilder} class is responsible for creating {@link Column} objects
 * from fields annotated with {@link CSVField}. It checks the type of the field to ensure
 * it is compatible with CSV serialization.
 */
public class ColumnBuilder {

    private static final Set<Class<?>> WRAPPER_CLASSES = new HashSet<>(Set.of(Integer.class, Double.class, Boolean.class, Character.class, Long.class, Short.class, Byte.class, Float.class));

    private ColumnBuilder() {
    }

    /**
     * Builds a {@link Column} object from the specified field.
     *
     * @param field the field annotated with {@link CSVField}
     * @return a {@link Column} object representing the given field
     * @throws IllegalCSVFieldType if the field's type is not supported for CSV serialization
     */
    public static Column buildColumn(final Field field) {
        String name = field.getAnnotation(CSVField.class).name();
        field.setAccessible(true);
        return new Column(name, getFieldType(field), field);
    }

    /**
     * Determines the type of the given field and returns its corresponding {@link FieldType}.
     *
     * @param field the field whose type is to be determined
     * @return the {@link FieldType} corresponding to the field's type
     * @throws IllegalCSVFieldType if the field's type is not supported for CSV serialization
     */
    private static FieldType getFieldType(final Field field) {
        Class<?> type = field.getType();
        if (isPrimitiveOrWrapperOrString(type)) {
            return FieldType.PRIMITIVE_OR_STRING;
        }

        if (type.isArray() && isPrimitiveOrWrapperOrString(type.getComponentType())) {
            return FieldType.PRIMITIVE_OR_STRING_ARRAY;
        }

        if (Collection.class.isAssignableFrom(type)) {
            Type genericFieldType = field.getGenericType();
            if (genericFieldType instanceof ParameterizedType aType) {
                Type[] fieldArgTypes = aType.getActualTypeArguments();
                if (fieldArgTypes.length == 1 && fieldArgTypes[0] != null && isPrimitiveOrWrapperOrString((Class<?>) fieldArgTypes[0])) {
                    return FieldType.PRIMITIVE_OR_STRING_COLLECTION;
                }
            }
        }
        throw new IllegalCSVFieldType(String.format("Field type '%s' is not supported", type));
    }

    private static boolean isPrimitiveOrWrapperOrString(final Class<?> type) {
        return type.isPrimitive() || WRAPPER_CLASSES.contains(type) || type.equals(String.class);
    }
}
