package ru.otus.homework.client;

import ru.otus.homework.atm.Nominal;

import java.util.List;

public interface Client {
    List<Nominal> giveBanknotsToAtm();
    int takeMoneyFromAtm();
}
