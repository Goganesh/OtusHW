package ru.otus.homework.types;

import lombok.Getter;
import ru.otus.homework.element.AbstractField;
import ru.otus.homework.element.Service;

import java.lang.reflect.Field;

public class VisitNull extends AbstractField{
    public VisitNull(Field field) {
        super(field);
    }

    @Override
    public void accept(Service service) {
        service.onVisit(this);
    }
}
