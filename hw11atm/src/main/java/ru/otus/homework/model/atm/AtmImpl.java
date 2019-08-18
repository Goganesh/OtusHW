package ru.otus.homework.model.atm;

import ru.otus.homework.exception.NoFundsInBalance;
import ru.otus.homework.exception.NotCorrectAmount;
import ru.otus.homework.exception.NotCorrectNominalForCasset;
import ru.otus.homework.model.client.Client;

import java.util.ArrayList;
import java.util.List;

public class AtmImpl implements Atm {

    private List<AbstractCasset> cassets = new ArrayList<>();

    public AtmImpl() {
        cassets.add(new CassetImpl(Banknot.Nominal.FIFTY));
        cassets.add(new CassetImpl(Banknot.Nominal.ONE_HUNDRED));
        cassets.add(new CassetImpl(Banknot.Nominal.TWO_HUNDRED));
        cassets.add(new CassetImpl(Banknot.Nominal.FIVE_HUNDRED));
    }

    @Override
    public int getTotalAmount() {
        int totalAmount = 0;
        for(AbstractCasset casset : cassets){
            totalAmount = totalAmount + casset.getTotalAmount();
        }
        return totalAmount;
    }

    @Override
    public void takeBanknotesFromClient(Client clientInterface) throws NotCorrectNominalForCasset {
        List<Banknot> banknots = clientInterface.giveBanknotsToAtm();
        for(Banknot banknot : banknots){
            takeBanknot( banknot);
        }
    }

    private void takeBanknot(Banknot banknot) throws NotCorrectNominalForCasset {
        for(AbstractCasset casset : cassets){
            if (casset.getNominalType().equals(banknot.getNominal())){
                casset.addBanknotToCasset(banknot);
            }
        }
    }

    @Override
    public void printAtmBalance(){
        for(AbstractCasset casset : cassets){
            System.out.println("Номинал - " + casset.getNominalType() + " кол-во купюр - " + casset.banknots.size());
        }
        System.out.println("Общая сумма - " + getTotalAmount());
    }

    @Override
    public  List<Banknot> giveBanknotesToClient( Client clientInterface) throws NoFundsInBalance, NotCorrectAmount {
        int money = clientInterface.takeMoneyFromAtm();
        if(money < 0 || money == 0){
            //System.out.println("Введите корректную сумма для выдачи");
            throw new NotCorrectAmount();
        }

        List<AbstractCasset> notNullCassets = this.findNotNullCassets();
        notNullCassets.sort(new CassetComporator());

        List<Banknot> banknotListForGiving = new ArrayList<>();

        for(AbstractCasset casset : notNullCassets){
            if (money < casset.getValueOfNominal(casset.nominalType)){
                continue;
            }
            int tempCount = casset.banknots.size();
            while(money < tempCount * casset.getValueOfNominal(casset.nominalType)){
                tempCount--;
            }
            casset.fillBanknotListForGiving(banknotListForGiving, tempCount, casset.nominalType);
            money = money - tempCount * casset.getValueOfNominal(casset.nominalType);
        }
        if (money != 0) {
            //System.out.println("Нет денег в банкомате");
            throw new NoFundsInBalance();
        }
        correctBalanceInAtm(banknotListForGiving);

        return banknotListForGiving;
    }

    private List<AbstractCasset> findNotNullCassets(){
        List<AbstractCasset> notNullCassets = new ArrayList<>();
        for(AbstractCasset casset : cassets){
            if(casset.banknots.size() != 0) {
                notNullCassets.add(casset);
            }
        }
        return notNullCassets;
    }

    private void correctBalanceInAtm( List<Banknot> banknotListForGiving){
        for(Banknot banknot : banknotListForGiving) {
            for(AbstractCasset casset : cassets){
                if(banknot.getNominal().equals(casset.nominalType)){
                    int index = casset.getBanknots().size()-1;
                    casset.getBanknots().remove(index);
                }
            }
        }
    }

}
