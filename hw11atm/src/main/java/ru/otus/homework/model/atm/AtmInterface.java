package ru.otus.homework.model.atm;

import ru.otus.homework.exception.NoFundsInBalance;
import ru.otus.homework.exception.NotCorrectAmount;
import ru.otus.homework.model.client.Client;
import ru.otus.homework.model.client.ClientInterface;

import java.util.List;

public interface AtmInterface {
    int getTotalAmount();
    void takeBanknotesFromClient(ClientInterface clientInterface);
    List<Banknot> giveBanknotesToClient(ClientInterface clientInterface) throws NoFundsInBalance, NotCorrectAmount;

}
