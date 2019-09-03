package ru.otus.homework.atm;

import lombok.Setter;
import ru.otus.homework.department.Department;
import ru.otus.homework.department.EventListener;
import ru.otus.homework.exception.NoFundsInBalance;
import ru.otus.homework.exception.NotCorrectAmount;
import ru.otus.homework.client.Client;

import java.util.ArrayList;
import java.util.List;

public class AtmImpl implements Atm, EventListener {
    @Setter
    private Department department;
    private MementoAtm mementoAtm;
    private List<Casset> cassets = new ArrayList<>();

    public AtmImpl(Department department) {
        this.department = department;
        cassets.add(new CassetImp(Nominal.FIFTY));
        cassets.add(new CassetImp(Nominal.ONE_HUNDRED));
        cassets.add(new CassetImp(Nominal.TWO_HUNDRED));
        cassets.add(new CassetImp(Nominal.FIVE_HUNDRED));
    }

    public AtmImpl(List<Casset> cassets) {
        this.cassets = cassets;
    }

    public void saveMemento(){
        mementoAtm = new MementoAtm(this.cassets);
    }

    private void undoChanges(){
        cassets = mementoAtm.getCassets();
    }

    @Override
    public void update(String eventType) {
        switch (eventType){
            case "updateTotalAmount":
                department.totalAmount = department.totalAmount + getTotalAmount();
                break;
            case "callFirstState":
                undoChanges();
                break;
            default:
                System.out.println("some mistake");
        }

    }

    @Override
    public int getTotalAmount() {
        int totalAmount = 0;
        for(Casset casset : cassets){
            totalAmount = totalAmount + casset.getTotalAmount();
        }
        return totalAmount;
    }

    @Override
    public void takeBanknotesFromClient(Client clientInterface) {
        List<Nominal> banknots = clientInterface.giveBanknotsToAtm();
        for(Nominal nominal : banknots){
            takeBanknot(nominal);
        }
    }

    private void takeBanknot(Nominal nominal) {
        for(Casset casset : cassets){
            if (casset.nominalType().equals(nominal)){
                casset.addBanknot(nominal);
            }
        }
    }

    @Override
    public void printAtmBalance(){
        for(Casset casset : cassets){
            System.out.println("Номинал - " + casset.nominalType() + " кол-во купюр - " + casset.getNominalCount());
        }
        System.out.println("Общая сумма - " + getTotalAmount());
    }

    @Override
    public  List<Nominal> giveBanknotesToClient( Client clientInterface) throws NoFundsInBalance, NotCorrectAmount {
        int money = clientInterface.takeMoneyFromAtm();
        if(money <= 0){
            //System.out.println("Введите корректную сумма для выдачи");
            throw new NotCorrectAmount();
        }

        List<Casset> notNullCassets = this.findNotNullCassets();
        notNullCassets.sort(new CassetComporator());

        List<Nominal> banknotListForGiving = new ArrayList<>();

        for(Casset casset : notNullCassets){
            if (money < casset.nominalType().value()){
                continue;
            }
            int tempCount = casset.getNominalCount();
            while(money < tempCount * casset.nominalType().value()){
                tempCount--;
            }
            for(int i = 0; i < tempCount; i++){
                banknotListForGiving.add(casset.nominalType());
            }

            money = money - tempCount * casset.nominalType().value();
        }
        if (money != 0) {
            //System.out.println("Нет денег в банкомате");
            throw new NoFundsInBalance();
        }

        return correctBalanceInAtm(banknotListForGiving);
    }

    private List<Casset> findNotNullCassets(){
        List<Casset> notNullCassets = new ArrayList<>();
        for(Casset casset : cassets){
            if(casset.getTotalAmount() != 0) {
                notNullCassets.add(casset);
            }
        }
        return notNullCassets;
    }

    private List<Nominal> correctBalanceInAtm( List<Nominal> banknotListForGiving){
        List<Nominal> result = new ArrayList<>();
        for(Nominal nominal : banknotListForGiving) {
            for(Casset casset : cassets){
                if(nominal.equals(casset.nominalType())){
                    result.add(casset.getBanknot());
                }
            }
        }
        return result;
    }

}
