package ru.chaplyginma.csvwriter.testdata.factory;

import ru.chaplyginma.csvwriter.testdata.domain.InvalidAnnotatedClass;
import ru.chaplyginma.csvwriter.testdata.domain.ValidAnnotatedClass;

import java.util.ArrayList;
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

    public static List<ValidAnnotatedClass> createValidAnnotatedClassObjectList() {
        List<ValidAnnotatedClass> validAnnotatedClassList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            validAnnotatedClassList.add(TestDataFactory.createIthValidAnnotatedClassObject(i));
        }
        return validAnnotatedClassList;
    }

    public static ValidAnnotatedClass createIthValidAnnotatedClassObject(int counter) {
        ValidAnnotatedClass validAnnotatedClass = new ValidAnnotatedClass();
        validAnnotatedClass.setIntField(counter);
        validAnnotatedClass.setStringField(String.format("%d'th String with (|) pipe", counter));
        validAnnotatedClass.setLongWrapperField(10_000_000_000L * counter);
        validAnnotatedClass.setFloatArrayField(new float[]{11f * counter, 22f * counter, 33f * counter, 44f * counter});
        validAnnotatedClass.setBooleanArrayField(new Boolean[]{true, false});
        validAnnotatedClass.setStringListField(List
                .of(
                        String.format("%d'th String with (,) coma", counter),
                        String.format("%d'th String with (;) semicolon", counter),
                        String.format("%d'th String with (\") double quote", counter)
                )
        );
        validAnnotatedClass.setIntegerSetField(Set.of(100 * counter, 200 * counter, 300 * counter));
        return validAnnotatedClass;
    }
}
