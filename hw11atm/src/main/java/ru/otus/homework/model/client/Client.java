package ru.otus.homework.model.client;

import ru.otus.homework.model.atm.Nominal;

import java.util.List;

public interface Client {
    List<Nominal> giveBanknotsToAtm();
    int takeMoneyFromAtm();
}
