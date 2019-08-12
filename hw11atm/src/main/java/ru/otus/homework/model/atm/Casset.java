package ru.otus.homework.model.atm;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Casset {
    @Setter
    @Getter
    private List<Banknot> banknots = new ArrayList<>();
    @Getter
    @Setter
    private Nominal nominal;
    @Setter
    @Getter
    private int count = 0;
    @Getter
    @Setter
    private int number;

    public Casset(Nominal nominal) {
        this.nominal = nominal;
        if(nominal.equals(Nominal.FIFTY)){
            number = 50;
        } else if (nominal.equals(Nominal.ONE_HUNDRED)){
            number = 100;
        } else if (nominal.equals(Nominal.TWO_HUNDRED)){
            number = 200;
        } else if (nominal.equals(Nominal.FIVE_HUNDRED)) {
            number = 500;
        }
    }

}
