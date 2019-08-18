package ru.otus.homework.model.client;

import ru.otus.homework.exception.NoFundsInBalance;
import ru.otus.homework.exception.NotCorrectAmount;
import ru.otus.homework.model.atm.Banknot;

import java.util.ArrayList;
import java.util.List;

public class ClientImpl implements Client {
    private List<Banknot> cash = new ArrayList<>();
    private int money = 0;

    public List<Banknot> getCash() {
        return cash;
    }

    public void setCash(List<Banknot> cash) {
        this.cash = cash;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public List<Banknot> giveBanknotsToAtm() {
        return cash;
    }

    @Override
    public int takeMoneyFromAtm() throws NoFundsInBalance, NotCorrectAmount {
        return money;
    }
}
