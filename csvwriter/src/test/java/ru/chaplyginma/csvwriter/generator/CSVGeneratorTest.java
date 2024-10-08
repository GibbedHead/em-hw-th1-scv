package ru.chaplyginma.csvwriter.generator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.chaplyginma.csvwriter.escaper.CSVEscaper;
import ru.chaplyginma.csvwriter.schema.CSVSchema;
import ru.chaplyginma.csvwriter.testdata.domain.ValidAnnotatedClass;
import ru.chaplyginma.csvwriter.testdata.factory.TestDataFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CSVGeneratorTest {

    private CSVGenerator<ValidAnnotatedClass> generator;
    private CSVEscaper escaper;

    @BeforeEach
    public void setUp() {
        CSVSchema schema = CSVSchema.forClass(ValidAnnotatedClass.class)
                .useHeader(true)
                .separator(",")
                .collectionSeparator(";")
                .quoteCharacter("\"")
                .build();
        escaper = new CSVEscaper(schema);
        generator = new CSVGenerator<>(schema, escaper);
    }

    @Test
    @DisplayName("Test use headers")
    void givenSchemaWithHeaders_whenGetCSV_thenStringWithHeaders() throws IllegalAccessException {
        String expected = """
                int,String,Long,Array of float,Array of Boolean,List of String,Set of Integer
                1,1'th String with (|) pipe,10000000000,"11.0;22.0;33.0;44.0","true;false","1'th String with (,) coma;1'th String with (;) semicolon;1'th String with ("") double quote","100;200;300"
                2,2'th String with (|) pipe,20000000000,"22.0;44.0;66.0;88.0","true;false","2'th String with (,) coma;2'th String with (;) semicolon;2'th String with ("") double quote","200;400;600"
                3,3'th String with (|) pipe,30000000000,"33.0;66.0;99.0;132.0","true;false","3'th String with (,) coma;3'th String with (;) semicolon;3'th String with ("") double quote","300;600;900"
                """;

        assertThat(generator.getCSV(TestDataFactory.createValidAnnotatedClassObjectList())).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test not use headers")
    void givenSchemaWithoutHeaders_whenGetCSV_thenStringWithoutHeaders() throws IllegalAccessException {
        String expected = """
                1,1'th String with (|) pipe,10000000000,"11.0;22.0;33.0;44.0","true;false","1'th String with (,) coma;1'th String with (;) semicolon;1'th String with ("") double quote","100;200;300"
                2,2'th String with (|) pipe,20000000000,"22.0;44.0;66.0;88.0","true;false","2'th String with (,) coma;2'th String with (;) semicolon;2'th String with ("") double quote","200;400;600"
                3,3'th String with (|) pipe,30000000000,"33.0;66.0;99.0;132.0","true;false","3'th String with (,) coma;3'th String with (;) semicolon;3'th String with ("") double quote","300;600;900"
                """;

        CSVSchema schemaWithoutHeader = CSVSchema.forClass(ValidAnnotatedClass.class)
                .useHeader(false)
                .separator(",")
                .collectionSeparator(";")
                .quoteCharacter("\"")
                .build();
        generator = new CSVGenerator<>(schemaWithoutHeader, escaper);

        assertThat(generator.getCSV(TestDataFactory.createValidAnnotatedClassObjectList())).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test null values")
    void givenNullValuesInObject_whenGetCSV_thenStringWithEmptyValueStringTemplate() throws IllegalAccessException {
        String expected = """
                int,String,Long,Array of float,Array of Boolean,List of String,Set of Integer
                1,,10000000000,,"true;false",,
                2,2'th String with (|) pipe,20000000000,"22.0;44.0;66.0;88.0","true;false","2'th String with (,) coma;2'th String with (;) semicolon;2'th String with ("") double quote","200;400;600"
                3,3'th String with (|) pipe,30000000000,"33.0;66.0;99.0;132.0","true;false","3'th String with (,) coma;3'th String with (;) semicolon;3'th String with ("") double quote","300;600;900"
                """;

        List<ValidAnnotatedClass> listWithNullFieldValues = TestDataFactory.createValidAnnotatedClassObjectList();
        ValidAnnotatedClass validAnnotatedClassObject = listWithNullFieldValues.get(0);
        validAnnotatedClassObject.setStringField(null);
        validAnnotatedClassObject.setIntegerSetField(null);
        validAnnotatedClassObject.setFloatArrayField(null);
        validAnnotatedClassObject.setStringListField(null);

        assertThat(generator.getCSV(listWithNullFieldValues)).isEqualTo(expected);
    }
}