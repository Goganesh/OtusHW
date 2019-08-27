package ru.otus.homework.model.atm;

public interface Casset {
    void addBanknot(Nominal nominal);
    Nominal getBanknot();
    int getTotalAmount();
    int getNominalCount();
    Nominal nominalType();
}
