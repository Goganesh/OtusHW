package ru.otus.homework.utils;

import ru.otus.homework.model.atm.Atm;
import ru.otus.homework.model.atm.Banknot;
import ru.otus.homework.model.atm.Casset;

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


    public static void printAtmBalance(Atm atm){
        for(Casset casset : atm.getCassets()){
            System.out.println("Номинал - " + casset.getNumber() + " кол-во купюр - " + casset.getCount());
        }
        System.out.println("Общая сумма - " + atm.getTotalAmount());
    }
}
