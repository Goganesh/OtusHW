package ru.otus.homework;

import ru.otus.homework.exception.NotCorrectNominalForCasset;
import ru.otus.homework.model.atm.*;
import ru.otus.homework.exception.NoFundsInBalance;
import ru.otus.homework.exception.NotCorrectAmount;
import ru.otus.homework.model.client.ClientImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws NoFundsInBalance, NotCorrectAmount, NotCorrectNominalForCasset {

        Atm atm = new AtmImpl();

        ClientImpl client = new ClientImpl();

        List<Nominal> banknots = new ArrayList<>();
        banknots.add(Nominal.FIVE_HUNDRED);
        banknots.add(Nominal.FIFTY);
        banknots.add(Nominal.ONE_HUNDRED);
        banknots.add(Nominal.FIVE_HUNDRED);
        banknots.add(Nominal.ONE_HUNDRED);
        banknots.add(Nominal.TWO_HUNDRED);
        banknots.add(Nominal.FIFTY);
        client.setCash(banknots);



        atm.printAtmBalance();
        atm.takeBanknotesFromClient(client);
        atm.printAtmBalance();
        System.out.println();

        client.setMoney(1500);
        List<Nominal> banknots1 = atm.giveBanknotesToClient(client);
        for(Nominal banknot : banknots1) {
            System.out.println(banknot);
        }

        atm.printAtmBalance();
        System.out.println();
    }

}
