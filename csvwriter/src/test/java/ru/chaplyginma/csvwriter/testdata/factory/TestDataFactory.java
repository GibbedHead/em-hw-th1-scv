package ru.chaplyginma.csvwriter.testdata.factory;

import ru.chaplyginma.csvwriter.testdata.domain.InvalidAnnotatedClass;
import ru.chaplyginma.csvwriter.testdata.domain.ValidAnnotatedClass;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestDataFactory {

    public static ValidAnnotatedClass createValidAnnotatedClassObject() {
        ValidAnnotatedClass validAnnotatedClass = new ValidAnnotatedClass();
        validAnnotatedClass.setIntField(11);
        validAnnotatedClass.setStringField("String with (|) pipe");
        validAnnotatedClass.setLongWrapperField(10_000_000_000L);
        validAnnotatedClass.setFloatArrayField(new float[]{11f, 22f, 33f, 44f});
        validAnnotatedClass.setBooleanArrayField(new Boolean[]{true, false});
        validAnnotatedClass.setStringListField(List.of("String with (,) coma", "String with (;) semicolon", "String with (\") double quote"));
        validAnnotatedClass.setIntegerSetField(Set.of(100, 200, 300));
        return validAnnotatedClass;
    }

    public static InvalidAnnotatedClass createInvalidAnnotatedClassObject() {
        ValidAnnotatedClass validAnnotatedClass = TestDataFactory.createValidAnnotatedClassObject();

        InvalidAnnotatedClass invalidAnnotatedClass = new InvalidAnnotatedClass();

        invalidAnnotatedClass.setIntField(validAnnotatedClass.getIntField());
        invalidAnnotatedClass.setStringField(validAnnotatedClass.getStringField());
        invalidAnnotatedClass.setLongWrapperField(validAnnotatedClass.getLongWrapperField());
        invalidAnnotatedClass.setFloatArrayField(validAnnotatedClass.getFloatArrayField());
        invalidAnnotatedClass.setBooleanArrayField(validAnnotatedClass.getBooleanArrayField());
        invalidAnnotatedClass.setStringListField(validAnnotatedClass.getStringListField());
        invalidAnnotatedClass.setIntegerSetField(validAnnotatedClass.getIntegerSetField());

        invalidAnnotatedClass.setMap(Map.of("Key1", "Value1", "Key2", "Value2", "Key3", "Value3", "Key4", "Value4"));

        return invalidAnnotatedClass;
    }
}
