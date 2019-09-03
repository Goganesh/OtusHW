package ru.otus.homework.client;

import lombok.Getter;
import lombok.Setter;
import ru.otus.homework.atm.Nominal;

import java.util.ArrayList;
import java.util.List;

public class ClientImpl implements Client {
    @Getter
    @Setter
    private List<Nominal> cash = new ArrayList<>();
    @Getter
    @Setter
    private int money = 0;

    @Override
    public List<Nominal> giveBanknotsToAtm() {
        return cash;
    }

    @Override
    public int takeMoneyFromAtm(){
        return money;
    }
}
