package ru.otus.homework.department;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.homework.atm.AtmImpl;
import ru.otus.homework.atm.Casset;
import ru.otus.homework.atm.CassetImp;
import ru.otus.homework.atm.Nominal;
import ru.otus.homework.client.Client;
import ru.otus.homework.client.ClientImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DepartmentTest {
    private Department department;
    private AtmImpl atm1;
    private AtmImpl atm2;
    private Client client;

    @Before
    public void beforeDepartmentTest(){
        List<Casset> cassetList1 = new ArrayList<>();
        Casset casset1 = new CassetImp(Nominal.FIVE_HUNDRED, 1);
        Casset casset2 = new CassetImp(Nominal.TWO_HUNDRED, 1);
        Casset casset3 = new CassetImp(Nominal.ONE_HUNDRED, 1);
        Casset casset4 = new CassetImp(Nominal.FIFTY, 1);
        cassetList1.add(casset1);
        cassetList1.add(casset2);
        cassetList1.add(casset3);
        cassetList1.add(casset4);

        List<Casset> cassetList2 = new ArrayList<>();
        Casset casset11 = new CassetImp(Nominal.FIVE_HUNDRED, 1);
        Casset casset22 = new CassetImp(Nominal.TWO_HUNDRED, 1);
        Casset casset33 = new CassetImp(Nominal.ONE_HUNDRED, 1);
        Casset casset44 = new CassetImp(Nominal.FIFTY, 1);
        cassetList2.add(casset11);
        cassetList2.add(casset22);
        cassetList2.add(casset33);
        cassetList2.add(casset44);

        client = new ClientImpl();
        List<Nominal> banknots = new ArrayList<>();
        banknots.add(Nominal.FIVE_HUNDRED);
        ((ClientImpl)client).setCash(banknots);

        department = new Department();
        atm1 = new AtmImpl(cassetList1);
        atm2 = new AtmImpl(cassetList2);
        department.addAtmToDepartment(atm1);
        department.addAtmToDepartment(atm2);
    }

    @After
    public void afterDepartmentTest(){
        department = null;
        atm1 = null;
        atm2 = null;
        client = null;
    }

    @Test
    public void updateTotalAmountTest() {
        atm1.takeBanknotesFromClient(client);
        department.updateTotalAmount();
        assertTrue(2200 == department.totalAmount);
    }

    @Test
    public void callFirstAtmStateTest() {
        atm1.saveMemento();
        atm2.saveMemento();
        atm1.takeBanknotesFromClient(client);
        department.callFirstAtmState();
        department.updateTotalAmount();
        assertTrue(1700 == department.totalAmount);
    }
}