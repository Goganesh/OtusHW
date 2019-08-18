package ru.otus.homework.model.atm;

import ru.otus.homework.exception.NotCorrectNominalForCasset;

import java.util.List;

public abstract class AbstractCasset {
    protected List<Banknot> banknots;
    protected final Banknot.Nominal nominalType;

    public AbstractCasset(Banknot.Nominal nominalType) {
        this.nominalType = nominalType;
    }

    abstract protected int getTotalAmount();
    abstract protected void addBanknotToCasset(Banknot banknot) throws NotCorrectNominalForCasset;
    abstract protected int getValueOfNominal(Banknot.Nominal nominal);
    abstract protected List<Banknot> fillBanknotListForGiving(List<Banknot> banknotListForGiving, int count, Banknot.Nominal nominal);
}
