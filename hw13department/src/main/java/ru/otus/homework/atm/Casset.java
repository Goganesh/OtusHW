package ru.otus.homework.atm;

public interface Casset {
    void addBanknot(Nominal nominal);
    Nominal getBanknot();
    int getTotalAmount();
    int getNominalCount();
    Nominal nominalType();
}
