package ru.otus.homework;

import ru.otus.homework.model.Atm;
import ru.otus.homework.model.Banknot;
import ru.otus.homework.model.Nominal;
import ru.otus.homework.exception.NoFundsInBalance;
import ru.otus.homework.exception.NotCorrectAmount;
import ru.otus.homework.utils.AtmUtils;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws NoFundsInBalance, NotCorrectAmount {

        Atm atm = new Atm();
        AtmUtils.printAtmBalance(atm);

        List<Banknot> banknots = new ArrayList<>();
        banknots.add(new Banknot(Nominal.FIVE_HUNDRED));
        banknots.add(new Banknot(Nominal.FIFTY));
        banknots.add(new Banknot(Nominal.ONE_HUNDRED));
        banknots.add(new Banknot(Nominal.FIVE_HUNDRED));
        banknots.add(new Banknot(Nominal.ONE_HUNDRED));
        banknots.add(new Banknot(Nominal.TWO_HUNDRED));
        banknots.add(new Banknot(Nominal.FIFTY));
        System.out.println();
        AtmUtils.takeBanknotes(atm, banknots);
        AtmUtils.printAtmBalance(atm);
        System.out.println();

        List<Banknot> banknots1 = AtmUtils.giveAllBanknots(atm);//giveBanknotes(atm,150000);
        for(Banknot banknot : banknots1) {
            System.out.println(banknot);
        }


        AtmUtils.printAtmBalance(atm);
        System.out.println();

    }

}
