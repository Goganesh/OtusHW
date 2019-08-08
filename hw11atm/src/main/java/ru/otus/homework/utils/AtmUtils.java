package ru.otus.homework.utils;

import ru.otus.homework.exception.NoFundsInBalance;
import ru.otus.homework.exception.NotCorrectAmount;
import ru.otus.homework.model.Atm;
import ru.otus.homework.model.Banknot;
import ru.otus.homework.model.Casset;

import java.util.ArrayList;
import java.util.List;

public class AtmUtils {

    public static List<Casset> findNotNullCassets(Atm atm){
        List<Casset> notNullCassets = new ArrayList<>();
        for(Casset casset : atm.getCassets()){
            if(casset.getCount() != 0) {
                notNullCassets.add(casset);
            }
        }
        return notNullCassets;
    }

    public static void correctBalanceInAtm(Atm atm, List<Banknot> banknotListForGiving){
        for(Banknot banknot : banknotListForGiving) {
            for(Casset casset : atm.getCassets()){
                if(banknot.getNominal().equals(casset.getNominal())){
                    int index = casset.getBanknots().size()-1;
                    casset.getBanknots().remove(index);
                    int count = casset.getCount();
                    casset.setCount(--count);
                }
            }
        }
    }

    public static int getTotalAmount(Atm atm) {
        int totalAmount = 0;
        for(Casset casset : atm.getCassets()){
            totalAmount = totalAmount + (casset.getNumber() * casset.getCount());
        }
        return totalAmount;
    }

    public static void printAtmBalance(Atm atm){
        for(Casset casset : atm.getCassets()){
            System.out.println("Номинал - " + casset.getNumber() + " кол-во купюр - " + casset.getCount());
        }
        System.out.println("Общая сумма - " + AtmUtils.getTotalAmount(atm));
    }

    public static void takeBanknotes(Atm atm, List<Banknot> banknots){
        for(Banknot banknot : banknots){
            takeBanknot(atm, banknot);
        }
    }

    public static void takeBanknot(Atm atm, Banknot banknot){
        for(Casset casset : atm.getCassets()){
            if (casset.getNominal().equals(banknot.getNominal())){
                casset.getBanknots().add(banknot);
                int count = casset.getCount();
                count++;
                casset.setCount(count);
            }
        }
    }

    public static List<Banknot> giveAllBanknots(Atm atm) throws NoFundsInBalance, NotCorrectAmount {
        return giveBanknotes(atm, getTotalAmount(atm));
    }

    public static List<Banknot> giveBanknotes(Atm atm, int money) throws NoFundsInBalance, NotCorrectAmount {
        if(money < 0 || money == 0){
            //System.out.println("Введите корректную сумма для выдачи");
            throw new NotCorrectAmount();
        }
        List<Casset> notNullCassets = AtmUtils.findNotNullCassets(atm);
        notNullCassets.sort(new CassetUtils());

        List<Banknot> banknotListForGiving = new ArrayList<>();

        for(Casset casset : notNullCassets){
            if (money < casset.getNumber()){
                continue;
            }
            int tempCount = casset.getCount();
            while(money < tempCount * casset.getNumber()){
                tempCount--;
            }
            banknotListForGiving = BanknotUtils.fillBanknotListForGiving(banknotListForGiving, tempCount, casset.getNominal());
            money = money - tempCount * casset.getNumber();
        }
        if (money != 0) {
            //System.out.println("Нет денег в банкомате");
            throw new NoFundsInBalance();
        }
        AtmUtils.correctBalanceInAtm(atm, banknotListForGiving);

        return banknotListForGiving;
    }

}
