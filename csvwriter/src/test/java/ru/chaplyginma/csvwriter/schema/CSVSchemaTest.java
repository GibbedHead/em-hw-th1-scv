package ru.chaplyginma.csvwriter.schema;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.chaplyginma.csvwriter.testdata.domain.InvalidAnnotatedClass;
import ru.chaplyginma.csvwriter.testdata.domain.ValidAnnotatedClass;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CSVSchemaTest {

    @Test
    @DisplayName("Test creating schema for valid annotated object")
    void givenValidAnnotatedObject_whenConstructSchema_thenSchemaCreated() {
        CSVSchema schema = CSVSchema.forClass(ValidAnnotatedClass.class)
                .build();

        assertThat(schema).isNotNull();
        assertThat(schema.getSeparator()).isEqualTo(",");
        assertThat(schema.getColumns()).hasSize(7);
    }

    @Test
    @DisplayName("Test creating schema for invalid annotated object")
    void givenInvalidAnnotatedObject_whenConstructSchema_thenThrowsIllegalCSVFieldTypeException() {
        assertThatThrownBy(
                () -> CSVSchema.forClass(InvalidAnnotatedClass.class)
                        .build()
        )
                .isInstanceOf(IllegalArgumentException.class);
    }

}