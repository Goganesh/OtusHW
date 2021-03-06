package ru.otus.homework.types;

import lombok.Getter;
import ru.otus.homework.element.AbstractField;
import ru.otus.homework.element.JsonAccumulator;

import java.lang.reflect.Field;

public class FinVisitArray extends AbstractField {
    @Getter
    private final Object obj;

    public FinVisitArray(Field field, Object array) {
        super(field);
        this.obj = array;
    }

    public void accept(JsonAccumulator jsonAccumulator){
        jsonAccumulator.onVisit(this);
    }
}
