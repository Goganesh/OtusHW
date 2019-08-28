package ru.otus.homework.model.atm;

import java.util.Comparator;

public class CassetComporator implements Comparator<Casset> {

    @Override
    public int compare(Casset o1, Casset o2) {
        return o2.nominalType().value()-o1.nominalType().value();
    }
}
