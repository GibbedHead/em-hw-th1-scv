package ru.chaplyginma.csvwriter.testdata.domain;

import ru.chaplyginma.csvwriter.annotation.CSVField;

import java.util.Map;

public class InvalidAnnotatedClass extends ValidAnnotatedClass {
    @CSVField(name = "Map")
    private Map<String, String> map;

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
