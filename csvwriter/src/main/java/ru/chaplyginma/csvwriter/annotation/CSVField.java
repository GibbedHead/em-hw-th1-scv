package ru.chaplyginma.csvwriter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@code CSVField} annotation is used to mark fields in a class that will be
 * serialized into a CSV (Comma-Separated Values) file. This annotation allows
 * for the specification of the column header name that will be used in the
 * resulting CSV file.
 *
 * <p>Each field annotated with {@code CSVField} will correspond to a column
 * in the CSV output, and the {@code name} attribute specifies the header
 * for that column.</p>
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CSVField {

    String name();
}
