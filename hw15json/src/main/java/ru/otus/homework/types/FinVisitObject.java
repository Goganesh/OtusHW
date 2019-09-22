package ru.otus.homework.types;

import lombok.Getter;
import ru.otus.homework.element.AbstractField;
import ru.otus.homework.element.Service;

import java.lang.reflect.Field;

public class FinVisitObject extends AbstractField {

    @Getter
    private final Object obj;

    public FinVisitObject(Field field, Object obj) {
        super(field);
        this.obj=obj;
    }

    @Override
    public void accept(Service service) {
        service.onVisit(this);
    }
}