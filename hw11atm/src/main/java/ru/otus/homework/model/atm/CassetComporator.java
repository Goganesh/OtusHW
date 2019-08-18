package ru.otus.homework.model.atm;

import java.util.Comparator;

public class CassetComporator implements Comparator<AbstractCasset> {

    @Override
    public int compare(AbstractCasset o1, AbstractCasset o2) {
        return o2.getValueOfNominal(o2.nominalType)-o1.getValueOfNominal(o1.nominalType);
    }
}
