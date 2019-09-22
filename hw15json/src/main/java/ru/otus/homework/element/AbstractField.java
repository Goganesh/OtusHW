package ru.otus.homework.element;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;

public abstract class AbstractField implements VisitType {
    @Getter
    private final Field field;

    protected AbstractField(Field field) {
        this.field = field;
    }
}
