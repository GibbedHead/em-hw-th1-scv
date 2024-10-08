package ru.chaplyginma.csvwriter.escaper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.chaplyginma.csvwriter.schema.CSVSchema;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CSVEscaperTest {

    private CSVEscaper escaper;

    @BeforeEach
    void setUp() {
        CSVSchema schema = CSVSchema.forClass(Object.class)
                .quoteCharacter("\"")
                .separator(",")
                .collectionSeparator(";")
                .build();
        escaper = new CSVEscaper(schema);
    }

    @ParameterizedTest
    @MethodSource("escapeTestCases")
    @DisplayName("Test escaping: single quote; quotes; separator; collection separator; simple string; empty string")
    void testEscape(String input, String expected) {
        assertThat(escaper.escape(input)).isEqualTo(expected);
    }

    static Stream<Arguments> escapeTestCases() {
        return Stream.of(
                Arguments.of("\"", "\"\"\"\""),
                Arguments.of("This is a \"quote\".", "This is a \"\"quote\"\"."),
                Arguments.of("value1,value2", "\"value1,value2\""),
                Arguments.of("item1;item2", "\"item1;item2\""),
                Arguments.of("Hello World!", "Hello World!"),
                Arguments.of("", "")
        );
    }

}