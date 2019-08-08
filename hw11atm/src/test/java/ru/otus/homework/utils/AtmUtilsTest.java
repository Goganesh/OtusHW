package ru.otus.homework.utils;

import org.junit.Before;
import org.junit.Test;
import ru.otus.homework.exception.NoFundsInBalance;
import ru.otus.homework.exception.NotCorrectAmount;
import ru.otus.homework.model.Atm;
import ru.otus.homework.model.Banknot;
import ru.otus.homework.model.Casset;
import ru.otus.homework.model.Nominal;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AtmUtilsTest {

    Atm atm;

    @Before
    public void before(){
        atm = new Atm();
    }

    public void after(){
        atm = null;
    }

    @Test
    public void findNotNullCassetsAtmNotEmpty() {
        List<Banknot> banknots = new ArrayList<>();
        banknots.add(new Banknot(Nominal.FIFTY));
        banknots.add(new Banknot(Nominal.ONE_HUNDRED));
        banknots.add(new Banknot(Nominal.FIFTY));

        AtmUtils.takeBanknotes(atm, banknots);
        List<Casset> notNullCassets = AtmUtils.findNotNullCassets(atm);
        assertTrue(notNullCassets.size()==2);
    }

    @Test
    public void findNotNullCassetsAtmEmpty() {
        List<Casset> notNullCassets = AtmUtils.findNotNullCassets(atm);
        assertTrue(notNullCassets.size()==0);
    }

    @Test
    public void testTakeBanknotes() {
        List<Banknot> banknots = new ArrayList<>();
        banknots.add(new Banknot(Nominal.FIFTY));
        banknots.add(new Banknot(Nominal.ONE_HUNDRED));
        banknots.add(new Banknot(Nominal.FIFTY));

        AtmUtils.takeBanknotes(atm, banknots);

        boolean firstCheck = false;
        boolean secondCheck = false;

        for(Casset casset : atm.getCassets()){
            if(casset.getNominal().equals(Nominal.FIFTY)){
                if(casset.getCount()==2){
                    firstCheck = true;
                }
            } else if (casset.getNominal().equals(Nominal.ONE_HUNDRED)){
                if(casset.getCount()==1){
                    secondCheck = true;
                }
            }
        }
        assertTrue(firstCheck&secondCheck);
    }

    @Test
    public void testTakeBanknot() {
        Banknot banknot = new Banknot(Nominal.FIFTY);
        AtmUtils.takeBanknot(atm, banknot);
        for(Casset casset : atm.getCassets()){
            if(casset.getNominal().equals(banknot.getNominal())){
                assertTrue(casset.getBanknots().get(0).equals(banknot));
            }
        }
    }

    @Test
    public void testNotCorrectCorrectAmountException() {
        boolean test1 = false;
        boolean test2 = false;
        try {
            AtmUtils.giveBanknotes(atm, -5);
        } catch (NotCorrectAmount e) {
            test1 = true;
        } catch (NoFundsInBalance noFundsInBalance) {
            noFundsInBalance.printStackTrace();
        }
        try {
            AtmUtils.giveBanknotes(atm, 0);
        } catch (NotCorrectAmount e) {
            test2 = true;
        } catch (NoFundsInBalance noFundsInBalance) {
            noFundsInBalance.printStackTrace();
        }

        assertTrue(test1&test2);
    }

    @Test
    public void testGiveBanknots() throws NoFundsInBalance, NotCorrectAmount {
        List<Banknot> banknots = new ArrayList<>();
        banknots.add(new Banknot(Nominal.ONE_HUNDRED));
        banknots.add(new Banknot(Nominal.TWO_HUNDRED));
        AtmUtils.takeBanknotes(atm, banknots);
        int tempAmount = AtmUtils.getTotalAmount(atm);
        List<Banknot> banknotList = AtmUtils.giveBanknotes(atm, 100);
        int amount = AtmUtils.getTotalAmount(atm);
        boolean checkAtmBalance = false;
        if(tempAmount - amount == 100){
            checkAtmBalance = true;
        }
        assertTrue(checkAtmBalance);
    }
}