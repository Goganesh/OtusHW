package ru.otus.homework.model.client;

import lombok.Getter;
import lombok.Setter;
import ru.otus.homework.exception.NoFundsInBalance;
import ru.otus.homework.exception.NotCorrectAmount;
import ru.otus.homework.model.atm.Banknot;

import java.util.ArrayList;
import java.util.List;

public class ClientImpl implements Client {
    @Getter
    @Setter
    private List<Banknot> cash = new ArrayList<>();
    @Getter
    @Setter
    private int money = 0;

    @Override
    public List<Banknot> giveBanknotsToAtm() {
        return cash;
    }

    @Override
    public int takeMoneyFromAtm() throws NoFundsInBalance, NotCorrectAmount {
        return money;
    }
}
