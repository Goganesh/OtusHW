package ru.otus.homework.department;
import ru.otus.homework.atm.Atm;
import ru.otus.homework.atm.AtmImpl;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private EventManager events;
    public int totalAmount = 0;
    private List<Atm> atmList = new ArrayList<>();

    public Department() {
        this.events = new EventManager("updateTotalAmount", "callFirstState");
    }

    public void updateTotalAmount(){
        totalAmount = 0;
        events.notify("updateTotalAmount");
    }

    public void callFirstAtmState(){
        events.notify("callFirstState");
    }

    public void addAtmToDepartment(Atm atm){
        ((AtmImpl)atm).setDepartment(this);
        atmList.add(atm);
        events.subscribe("updateTotalAmount", (EventListener) atm);
        events.subscribe("callFirstState", (EventListener) atm);
    }
    public void removeAtmFromDepartment(Atm atm){
        atmList.remove(atm);
        events.unsubscribe("updateTotalAmount", (EventListener) atm);
        events.unsubscribe("callFirstState", (EventListener) atm);
    }
}
