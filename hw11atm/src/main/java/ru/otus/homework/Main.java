package ru.otus.homework;

import ru.otus.homework.model.atm.Atm;
import ru.otus.homework.model.atm.Banknot;
import ru.otus.homework.model.atm.Nominal;
import ru.otus.homework.exception.NoFundsInBalance;
import ru.otus.homework.exception.NotCorrectAmount;
import ru.otus.homework.model.client.Client;
import ru.otus.homework.utils.AtmUtils;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws NoFundsInBalance, NotCorrectAmount {

        Client client = new Client();

        List<Banknot> banknots = new ArrayList<>();
        banknots.add(new Banknot(Nominal.FIVE_HUNDRED));
        banknots.add(new Banknot(Nominal.FIFTY));
        banknots.add(new Banknot(Nominal.ONE_HUNDRED));
        banknots.add(new Banknot(Nominal.FIVE_HUNDRED));
        banknots.add(new Banknot(Nominal.ONE_HUNDRED));
        banknots.add(new Banknot(Nominal.TWO_HUNDRED));
        banknots.add(new Banknot(Nominal.FIFTY));
        client.setCash(banknots);

        Atm atm = new Atm();
        AtmUtils.printAtmBalance(atm);
        atm.takeBanknotesFromClient(client);
        AtmUtils.printAtmBalance(atm);
        System.out.println();

        client.setMoney(1500);
        List<Banknot> banknots1 = atm.giveBanknotesToClient(client);
        for(Banknot banknot : banknots1) {
            System.out.println(banknot);
        }


        AtmUtils.printAtmBalance(atm);
        System.out.println();

    }

}
