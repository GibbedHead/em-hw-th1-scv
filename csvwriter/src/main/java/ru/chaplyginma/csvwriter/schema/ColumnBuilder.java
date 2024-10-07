package ru.chaplyginma.csvwriter.schema;

import ru.chaplyginma.csvwriter.annotation.CSVField;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class ColumnBuilder {

    private static final Set<Class<?>> WRAPPER_CLASSES = new HashSet<>(Set.of(Integer.class, Double.class, Boolean.class, Character.class, Long.class, Short.class, Byte.class, Float.class));

    private ColumnBuilder() {
    }

    public static Column buildColumn(final Field field) {
        String name = field.getAnnotation(CSVField.class).name();
        Class<?> type = field.getType();
        FieldType fieldType = FieldType.PRIMITIVE;
        if (WRAPPER_CLASSES.contains(type)) {
            fieldType = FieldType.PRIMITIVE_WRAPPER;
        }
        if (type.equals(String.class)) {
            fieldType = FieldType.STRING;
        }
        if (type.isArray() && type.getComponentType().isPrimitive()) {
            fieldType = FieldType.PRIMITIVE_ARRAY;
        }
        if (type.isArray() && WRAPPER_CLASSES.contains(type.getComponentType())) {
            fieldType = FieldType.PRIMITIVE_WRAPPER_ARRAY;
        }
        if (type.isArray() && type.getComponentType().equals(String.class)) {
            fieldType = FieldType.STRING_ARRAY;
        }
        field.setAccessible(true);
        return new Column(name, fieldType, field);
    }
}
