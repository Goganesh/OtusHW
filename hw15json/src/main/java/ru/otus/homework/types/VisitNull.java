package ru.otus.homework.types;

import ru.otus.homework.element.AbstractField;
import ru.otus.homework.element.JsonAccumulator;

import java.lang.reflect.Field;

public class VisitNull extends AbstractField{
    public VisitNull(Field field) {
        super(field);
    }

    @Override
    public void accept(JsonAccumulator jsonAccumulator) {
        jsonAccumulator.onVisit(this);
    }
}
