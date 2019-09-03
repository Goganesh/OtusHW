package ru.otus.homework;

import ru.otus.homework.atm.*;
import ru.otus.homework.client.ClientImpl;
import ru.otus.homework.department.Department;
import ru.otus.homework.department.EventListener;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Casset> cassetList = new ArrayList<>();
        Casset casset1 = new CassetImp(Nominal.FIVE_HUNDRED, 1);
        Casset casset2 = new CassetImp(Nominal.TWO_HUNDRED, 1);
        Casset casset3 = new CassetImp(Nominal.ONE_HUNDRED, 1);
        Casset casset4 = new CassetImp(Nominal.FIFTY, 1);
        cassetList.add(casset1);
        cassetList.add(casset2);
        cassetList.add(casset3);
        cassetList.add(casset4);


        Department department = new Department();
        AtmImpl atm1 = new AtmImpl(cassetList);
        AtmImpl atm2 = new AtmImpl(cassetList);

        atm1.saveMemento();
        atm2.saveMemento();

        department.addAtmToDepartment(atm1);
        department.addAtmToDepartment(atm2);

        department.updateTotalAmount();
        System.out.println(department.totalAmount);

        ClientImpl client = new ClientImpl();

        List<Nominal> banknots = new ArrayList<>();
        banknots.add(Nominal.FIVE_HUNDRED);
        client.setCash(banknots);
        atm1.takeBanknotesFromClient(client);
        department.updateTotalAmount();
        System.out.println(department.totalAmount);
        department.callFirstAtmState();
        department.updateTotalAmount();
        System.out.println(department.totalAmount);
    }

}
