package ru.otus.homework.model.atm;

import ru.otus.homework.exception.NotCorrectNominalForCasset;

import java.util.ArrayList;
import java.util.List;

public class CassetImpl extends AbstractCasset {

    public CassetImpl(Banknot.Nominal nominalType) {
        super(nominalType);
        banknots = new ArrayList<>();
    }

    @Override
    public int getTotalAmount() {
        int totalAmount = 0;
        if(banknots.size() > 0) {
            totalAmount = banknots.size() * banknots.get(0).getValue();
        }
        return totalAmount;
    }

    @Override
    public void addBanknotToCasset(Banknot banknot) throws NotCorrectNominalForCasset {
        if(banknot.getNominal().equals(nominalType)){
            banknots.add(banknot);
        } else {
            throw new NotCorrectNominalForCasset();
        }
    }

    @Override
    protected int getValueOfNominal(Banknot.Nominal nominal){
        int value = 0;
        if(nominal.equals(Banknot.Nominal.FIFTY)){
            value = 50;
        } else if (nominal.equals(Banknot.Nominal.ONE_HUNDRED)){
            value = 100;
        } else if (nominal.equals(Banknot.Nominal.TWO_HUNDRED)){
            value = 200;
        } else if (nominal.equals(Banknot.Nominal.FIVE_HUNDRED)) {
            value = 500;
        }
        return value;
    }

    @Override
    public List<Banknot> fillBanknotListForGiving(List<Banknot> banknotListForGiving, int count, Banknot.Nominal nominal){
        for(int i = 0; i < count; i++){
            banknotListForGiving.add(new Banknot(nominal));
        }
        return banknotListForGiving;
    }

}
