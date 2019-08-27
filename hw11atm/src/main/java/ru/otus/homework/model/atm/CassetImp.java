package ru.otus.homework.model.atm;

public class CassetImp implements Casset {
    private Nominal nominal;
    private int banknotCount = 0;

    public CassetImp(Nominal nominal) {
        this.nominal = nominal;
    }

    @Override
    public Nominal nominalType() {
        return nominal;
    }

    @Override
    public int getTotalAmount() {
        return banknotCount * nominal.value();
    }
    @Override
    public void addBanknot(Nominal nominal){
        banknotCount++;
    }
    @Override
    public Nominal getBanknot(){
        banknotCount--;
        return nominal;
    }

    @Override
    public int getNominalCount() {
        return banknotCount;
    }
}
