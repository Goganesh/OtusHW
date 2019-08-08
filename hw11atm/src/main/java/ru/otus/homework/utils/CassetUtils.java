package ru.otus.homework.utils;

import ru.otus.homework.model.Casset;

import java.util.Comparator;

public class CassetUtils implements Comparator<Casset> {
    @Override
    public int compare(Casset o1, Casset o2) {
        return o2.getNumber()-o1.getNumber();
    }
}
