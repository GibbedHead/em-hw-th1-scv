package ru.chaplyginma.csvwriter.schema;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.chaplyginma.csvwriter.annotation.CSVField;
import ru.chaplyginma.csvwriter.exception.IllegalCSVFieldType;
import ru.chaplyginma.csvwriter.testdata.domain.InvalidAnnotatedClass;
import ru.chaplyginma.csvwriter.testdata.domain.ValidAnnotatedClass;
import ru.chaplyginma.csvwriter.testdata.factory.TestDataFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ColumnBuilderTest {

    @Test
    @DisplayName("Test valid annotated object build column")
    void givenValidAnnotatedObject_whenBuildColumn_thenReturnColumn() {
        List<Column> columns = new ArrayList<>();

        ValidAnnotatedClass validAnnotatedClass = TestDataFactory.createValidAnnotatedClassObject();

        Class<? extends ValidAnnotatedClass> validAnnotatedClassClass = validAnnotatedClass.getClass();

        Field[] fields = validAnnotatedClassClass.getDeclaredFields();

        assertThat(fields).isNotNull().isNotEmpty();

        for (Field field : fields) {
            if (field.isAnnotationPresent(CSVField.class)) {
                columns.add(ColumnBuilder.buildColumn(field));
            }
        }

        assertThat(columns).hasSize(7);
        assertThat(columns.get(0).name()).isEqualTo("int");
        assertThat(columns.get(0).type()).isEqualTo(FieldType.PRIMITIVE_OR_STRING);
        assertThat(columns.get(0).field().getName()).isEqualTo("intField");
    }

    @Test
    @DisplayName("Test invalid annotated object build column")
    void givenInvalidAnnotatedObject_whenBuildColumn_thenThrowIllegalCSVFieldTypeException() {
        InvalidAnnotatedClass invalidAnnotatedClass = TestDataFactory.createInvalidAnnotatedClassObject();

        Class<? extends ValidAnnotatedClass> invalidAnnotatedClassClass = invalidAnnotatedClass.getClass();

        Field[] fields = invalidAnnotatedClassClass.getDeclaredFields();

        assertThat(fields).isNotNull().isNotEmpty();

        for (Field field : fields) {
            if (field.isAnnotationPresent(CSVField.class) && field.getName().equals("map")) {
                assertThatThrownBy(
                        () -> ColumnBuilder.buildColumn(field)
                ).isInstanceOf(IllegalCSVFieldType.class)
                        .hasMessageContaining(" is not supported");
            }

        }
    }

}