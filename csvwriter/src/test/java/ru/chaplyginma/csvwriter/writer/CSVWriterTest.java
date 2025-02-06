package ru.chaplyginma.csvwriter.writer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.chaplyginma.csvwriter.exception.CSVWriterException;
import ru.chaplyginma.csvwriter.exception.CreateSaveDirException;
import ru.chaplyginma.csvwriter.exception.FieldValueAccessException;
import ru.chaplyginma.csvwriter.file.CSVFileWriter;
import ru.chaplyginma.csvwriter.generator.CSVGenerator;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class CSVWriterTest {

    private CSVGenerator csvGeneratorMock;
    private CSVFileWriter fileWriterMock;

    @BeforeEach
    void setUp() {
        csvGeneratorMock = mock(CSVGenerator.class);
        fileWriterMock = mock(CSVFileWriter.class);
    }

    @Test
    void testWriteSuccess() throws CSVWriterException, FieldValueAccessException, CreateSaveDirException, IOException {
        Collection<String> data = Arrays.asList("row1", "row2");

        CSVWriter csvWriter = CSVWriter.testInstance(data, csvGeneratorMock, fileWriterMock);

        String expectedCsv = "row1,row2\n";
        String path = "test/output.csv";

        given(csvGeneratorMock.getCSV(data)).willReturn(expectedCsv);

        csvWriter.write(path);

        verify(fileWriterMock).writeCsv(path, expectedCsv);
    }

    @Test
    void testWriteThrowsIOException() throws CSVWriterException, FieldValueAccessException, CreateSaveDirException, IOException {
        Collection<String> data = Arrays.asList("row1", "row2");

        CSVWriter csvWriter = CSVWriter.testInstance(data, csvGeneratorMock, fileWriterMock);

        String path = "test/output.csv";
        String expectedCsv = "row1,row2\n";

        given(csvGeneratorMock.getCSV(data)).willReturn(expectedCsv);

        doThrow(new IOException("File error")).when(fileWriterMock).writeCsv(path, expectedCsv);

        assertThatThrownBy(() -> csvWriter.write(path))
                .isInstanceOf(CSVWriterException.class)
                .hasMessage("File error");
    }

    @Test
    void testWriteThrowsCreateSaveDirException() throws Exception {
        Collection<String> data = Arrays.asList("row1", "row2");

        CSVWriter csvWriter = CSVWriter.testInstance(data, csvGeneratorMock, fileWriterMock);

        String expectedCsv = "row1,row2\n";
        String path = "invalid/path/output.csv";

        given(csvGeneratorMock.getCSV(data)).willReturn(expectedCsv);

        doThrow(new CreateSaveDirException("Unable to create directory")).when(fileWriterMock).writeCsv(anyString(), anyString());

        assertThatThrownBy(() -> csvWriter.write(path))
                .isInstanceOf(CSVWriterException.class)
                .hasMessage("Unable to create directory");
    }
}
