package ru.otus.homework.atm;

import ru.otus.homework.department.EventListener;
import ru.otus.homework.exception.NoFundsInBalance;
import ru.otus.homework.exception.NotCorrectAmount;
import ru.otus.homework.client.Client;

import java.util.List;

public interface Atm {
    int getTotalAmount();
    void takeBanknotesFromClient(Client clientInterface);
    List<Nominal> giveBanknotesToClient(Client clientInterface) throws NoFundsInBalance, NotCorrectAmount;
    void printAtmBalance();
}
