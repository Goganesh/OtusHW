package ru.otus.homework.model.client;

import ru.otus.homework.exception.NoFundsInBalance;
import ru.otus.homework.exception.NotCorrectAmount;
import ru.otus.homework.model.atm.AtmInterface;
import ru.otus.homework.model.atm.Banknot;

import java.util.List;

public interface ClientInterface {
    List<Banknot> giveBanknotsToAtm();
    int takeMoneyFromAtm() throws NoFundsInBalance, NotCorrectAmount;
}
