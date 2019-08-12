package ru.otus.homework.utils;

import org.junit.Before;
import org.junit.Test;
import ru.otus.homework.exception.NoFundsInBalance;
import ru.otus.homework.exception.NotCorrectAmount;
import ru.otus.homework.model.atm.Atm;
import ru.otus.homework.model.atm.Banknot;
import ru.otus.homework.model.atm.Casset;
import ru.otus.homework.model.atm.Nominal;
import ru.otus.homework.model.client.Client;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AtmUtilsTest {

    Atm atm;
    Client client;

    @Before
    public void before(){
        atm = new Atm();
        client = new Client();
    }

    public void after(){
        atm = null;
        client = null;
    }

    @Test
    public void findNotNullCassetsAtmNotEmpty() {
        List<Banknot> banknots = new ArrayList<>();
        banknots.add(new Banknot(Nominal.FIFTY));
        banknots.add(new Banknot(Nominal.ONE_HUNDRED));
        banknots.add(new Banknot(Nominal.FIFTY));
        client.setCash(banknots);
        atm.takeBanknotesFromClient(client);
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
        client.setCash(banknots);
        atm.takeBanknotesFromClient(client);

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
        atm.takeBanknot(banknot);
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
            client.setMoney(-5);
            atm.giveBanknotesToClient(client);
        } catch (NotCorrectAmount e) {
            test1 = true;
        } catch (NoFundsInBalance noFundsInBalance) {
            noFundsInBalance.printStackTrace();
        }
        try {
            client.setMoney(0);
            atm.giveBanknotesToClient(client);
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
        client.setCash(banknots);
        atm.takeBanknotesFromClient(client);
        int tempAmount = atm.getTotalAmount();
        client.setMoney(100);
        List<Banknot> banknotList = atm.giveBanknotesToClient(client);
        int amount = atm.getTotalAmount();
        boolean checkAtmBalance = false;
        if(tempAmount - amount == 100){
            checkAtmBalance = true;
        }
        assertTrue(checkAtmBalance);
    }
}