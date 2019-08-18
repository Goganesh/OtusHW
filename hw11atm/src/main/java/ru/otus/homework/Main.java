package ru.otus.homework;

import ru.otus.homework.exception.NotCorrectNominalForCasset;
import ru.otus.homework.model.atm.*;
import ru.otus.homework.exception.NoFundsInBalance;
import ru.otus.homework.exception.NotCorrectAmount;
import ru.otus.homework.model.atm.Banknot;
import ru.otus.homework.model.client.ClientImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws NoFundsInBalance, NotCorrectAmount, NotCorrectNominalForCasset {
        ClientImpl client = new ClientImpl();

        List<Banknot> banknots = new ArrayList<>();
        banknots.add(new Banknot(Banknot.Nominal.FIVE_HUNDRED));
        banknots.add(new Banknot(Banknot.Nominal.FIFTY));
        banknots.add(new Banknot(Banknot.Nominal.ONE_HUNDRED));
        banknots.add(new Banknot(Banknot.Nominal.FIVE_HUNDRED));
        banknots.add(new Banknot(Banknot.Nominal.ONE_HUNDRED));
        banknots.add(new Banknot(Banknot.Nominal.TWO_HUNDRED));
        banknots.add(new Banknot(Banknot.Nominal.FIFTY));
        client.setCash(banknots);

        Atm atm = new AtmImpl();

        atm.printAtmBalance();
        atm.takeBanknotesFromClient(client);
        atm.printAtmBalance();
        System.out.println();

        client.setMoney(1500);
        List<Banknot> banknots1 = atm.giveBanknotesToClient(client);
        for(Banknot banknot : banknots1) {
            System.out.println(banknot);
        }

        atm.printAtmBalance();
        System.out.println();

    }

}
