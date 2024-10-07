package ru.chaplyginma.csvwriter.testdata.domain;

import ru.chaplyginma.csvwriter.annotation.CSVField;

import java.util.List;
import java.util.Queue;
import java.util.Set;

public class ValidAnnotatedClass {
    @CSVField(name = "int")
    private int intField;
    @CSVField(name = "String")
    private String stringField;
    @CSVField(name = "Long")
    private Long longWrapperField;
    @CSVField(name = "Array of float")
    private float[] floatArrayField;
    @CSVField(name = "Array of Boolean")
    private Boolean[] booleanArrayField;
    @CSVField(name = "List of String")
    private List<String> stringListField;
    @CSVField(name = "Set of Integer")
    private Set<Integer> integerSetField;
    private Queue<Double> notAnnotatedDoubleField;

    public int getIntField() {
        return intField;
    }

    public void setIntField(int intField) {
        this.intField = intField;
    }

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

    public Long getLongWrapperField() {
        return longWrapperField;
    }

    public void setLongWrapperField(Long longWrapperField) {
        this.longWrapperField = longWrapperField;
    }

    public float[] getFloatArrayField() {
        return floatArrayField;
    }

    public void setFloatArrayField(float[] floatArrayField) {
        this.floatArrayField = floatArrayField;
    }

    public Boolean[] getBooleanArrayField() {
        return booleanArrayField;
    }

    public void setBooleanArrayField(Boolean[] booleanArrayField) {
        this.booleanArrayField = booleanArrayField;
    }

    public List<String> getStringListField() {
        return stringListField;
    }

    public void setStringListField(List<String> stringListField) {
        this.stringListField = stringListField;
    }

    public Set<Integer> getIntegerSetField() {
        return integerSetField;
    }

    public void setIntegerSetField(Set<Integer> integerSetField) {
        this.integerSetField = integerSetField;
    }

    public Queue<Double> getNotAnnotatedDoubleField() {
        return notAnnotatedDoubleField;
    }

    public void setNotAnnotatedDoubleField(Queue<Double> notAnnotatedDoubleField) {
        this.notAnnotatedDoubleField = notAnnotatedDoubleField;
    }
}
