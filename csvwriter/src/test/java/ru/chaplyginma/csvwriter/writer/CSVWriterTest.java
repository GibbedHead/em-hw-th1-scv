package ru.chaplyginma.csvwriter.writer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.chaplyginma.csvwriter.schema.CSVSchema;
import ru.chaplyginma.csvwriter.testdata.domain.ValidAnnotatedClass;

class CSVWriterTest {

    @Test
    @DisplayName("Test use headers")
    void testValidAnnotatedClassWriting() {
        CSVSchema schema = CSVSchema.forClass(ValidAnnotatedClass.class)
                .useHeader(true)
                .build();

        //CSVWriter<ValidAnnotatedClass> csvWriter = new CSVWriter<>(schema);
    }

    @Test
    @DisplayName("Test not use headers")
    void testInvalidAnnotatedClassWriting() {
        //TODO fff
    }

    @Test
    @DisplayName("Test quotes escaping")
    void testQuoteEscaping() {
        //TODO ggg
    }

    @Test
    @DisplayName("Test collection saving")
    void testCollectionSaving() {
        //TODO ;;;
    }

    @Test
    @DisplayName("Test array saving")
    void testArraySaving() {
        //TODO ff
    }

    @Test
    @DisplayName("Test empty collection")
    void testEmptyCollection() {
        //TODO 55
    }

    @Test
    @DisplayName("Test collection with nulls")
    void testCollectionWithNulls() {
        //TODO df
    }

    @Test
    @DisplayName("Test array with nulls")
    void testArrayWithNulls() {
        //TODO f
    }

    @Test
    @DisplayName("Test directory creation")
    void testDirectoryCreation() {
        //TODO dd
    }
}