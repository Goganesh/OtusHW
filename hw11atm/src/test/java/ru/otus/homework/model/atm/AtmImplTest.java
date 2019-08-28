package ru.otus.homework.model.atm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.homework.exception.NoFundsInBalance;
import ru.otus.homework.exception.NotCorrectAmount;
import ru.otus.homework.model.client.ClientImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AtmImplTest {
    ClientImpl client;
    Atm atm;


    @Before
    public void createInfo(){
        client = new ClientImpl();
        atm = new AtmImpl();
    }

    @After
    public void cleanInfo(){
        client = null;
        atm = null;
    }

    @Test
    public void takeBanknotesFromClient() {
        List<Nominal> banknots = new ArrayList<>();
        banknots.add(Nominal.FIVE_HUNDRED);
        banknots.add(Nominal.FIFTY);
        banknots.add(Nominal.FIFTY);
        banknots.add(Nominal.ONE_HUNDRED);
        banknots.add(Nominal.ONE_HUNDRED);
        banknots.add(Nominal.FIVE_HUNDRED);
        banknots.add(Nominal.ONE_HUNDRED);
        banknots.add(Nominal.TWO_HUNDRED);
        banknots.add(Nominal.FIFTY);
        client.setCash(banknots);
        atm.takeBanknotesFromClient(client);
        assertTrue(atm.getTotalAmount() == 1650);
    }

    @Test
    public void giveBanknotesToClientNotCorrectAmountException() throws NoFundsInBalance {
        boolean result = false;
        try {
            atm.giveBanknotesToClient(client);
        } catch (NotCorrectAmount ex){
            result = true;
        }
        assertTrue(result);
    }
    @Test
    public void giveBanknotesToClientNoFundsInBalanceException() throws NotCorrectAmount {
        boolean result = false;
        try {
            client.setMoney(5000);
            atm.giveBanknotesToClient(client);
        } catch (NoFundsInBalance ex){
            result = true;
        }
        assertTrue(result);
    }

    @Test
    public void giveBanknotesToClient(){
        boolean result = false;
        try {
            List<Nominal> banknots = new ArrayList<>();
            banknots.add(Nominal.FIVE_HUNDRED);
            banknots.add(Nominal.FIVE_HUNDRED);
            banknots.add(Nominal.FIVE_HUNDRED);
            banknots.add(Nominal.FIVE_HUNDRED);
            banknots.add(Nominal.TWO_HUNDRED);
            banknots.add(Nominal.ONE_HUNDRED);
            banknots.add(Nominal.ONE_HUNDRED);
            banknots.add(Nominal.ONE_HUNDRED);
            banknots.add(Nominal.FIFTY);
            banknots.add(Nominal.FIFTY);
            banknots.add(Nominal.FIFTY);
            client.setCash(banknots);
            atm.takeBanknotesFromClient(client);
            client.setMoney(1650);
            List<Nominal> nominals = atm.giveBanknotesToClient(client);

            int sum = 0;
            for(Nominal nominal : nominals){
                sum = sum + nominal.value();
            }
            if(sum == 1650){
                result = true;
            }
        } catch (Exception ex){
            result = false;
        }
        assertTrue(result);
    }
}