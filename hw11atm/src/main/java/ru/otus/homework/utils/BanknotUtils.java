package ru.otus.homework.utils;

import ru.otus.homework.model.atm.Banknot;
import ru.otus.homework.model.atm.Nominal;

import java.util.List;

public class BanknotUtils {
    public static List<Banknot> fillBanknotListForGiving(List<Banknot> banknotListForGiving, int count, Nominal nominal){
        for(int i = 0; i < count; i++){
            banknotListForGiving.add(new Banknot(nominal));
        }
        return banknotListForGiving;
    }
}
