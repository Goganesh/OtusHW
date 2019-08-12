package ru.otus.homework.model.atm;

import lombok.Getter;
import lombok.Setter;
import ru.otus.homework.exception.NoFundsInBalance;
import ru.otus.homework.exception.NotCorrectAmount;
import ru.otus.homework.model.client.ClientInterface;
import ru.otus.homework.utils.AtmUtils;
import ru.otus.homework.utils.BanknotUtils;
import ru.otus.homework.utils.CassetUtils;

import java.util.ArrayList;
import java.util.List;

public class Atm implements AtmInterface {
    @Getter
    @Setter
    private List<Casset> cassets = new ArrayList<>();

    public Atm() {
        cassets.add(new Casset(Nominal.FIFTY));
        cassets.add(new Casset(Nominal.ONE_HUNDRED));
        cassets.add(new Casset(Nominal.TWO_HUNDRED));
        cassets.add(new Casset(Nominal.FIVE_HUNDRED));
    }

    @Override
    public int getTotalAmount() {
        int totalAmount = 0;
        for(Casset casset : getCassets()){
            totalAmount = totalAmount + (casset.getNumber() * casset.getCount());
        }
        return totalAmount;
    }

    @Override
    public void takeBanknotesFromClient(ClientInterface clientInterface){
        List<Banknot> banknots = clientInterface.giveBanknotsToAtm();
        for(Banknot banknot : banknots){
            takeBanknot( banknot);
        }
    }

    public void takeBanknot(Banknot banknot){
        for(Casset casset : getCassets()){
            if (casset.getNominal().equals(banknot.getNominal())){
                casset.getBanknots().add(banknot);
                int count = casset.getCount();
                count++;
                casset.setCount(count);
            }
        }
    }

    @Override
    public  List<Banknot> giveBanknotesToClient( ClientInterface clientInterface) throws NoFundsInBalance, NotCorrectAmount {
        int money = clientInterface.takeMoneyFromAtm();
        if(money < 0 || money == 0){
            //System.out.println("Введите корректную сумма для выдачи");
            throw new NotCorrectAmount();
        }
        List<Casset> notNullCassets = AtmUtils.findNotNullCassets(this);
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
        AtmUtils.correctBalanceInAtm(this, banknotListForGiving);

        return banknotListForGiving;
    }

}
