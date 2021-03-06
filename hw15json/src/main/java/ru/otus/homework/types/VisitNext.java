package ru.otus.homework.types;

import lombok.Getter;
import ru.otus.homework.element.AbstractField;
import ru.otus.homework.element.JsonAccumulator;

import java.lang.reflect.Field;

public class VisitNext extends AbstractField {

    @Getter
    private final Object obj;

    public VisitNext(Field field, Object obj) {
        super(field);
        this.obj=obj;
    }

    @Override
    public void accept(JsonAccumulator jsonAccumulator) {
        jsonAccumulator.onVisit(this);
    }
}