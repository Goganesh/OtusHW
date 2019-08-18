package ru.otus.homework.model.atm;

import lombok.Getter;
import lombok.Setter;

public class Banknot {
    @Getter
    @Setter
    private Nominal nominal;

    @Getter
    @Setter
    private int value;

    public Banknot(Nominal nominal) {
        this.nominal = nominal;
        if(nominal.equals(Nominal.FIFTY)){
            value = 50;
        } else if (nominal.equals(Nominal.ONE_HUNDRED)){
            value = 100;
        } else if (nominal.equals(Nominal.TWO_HUNDRED)){
            value = 200;
        } else if (nominal.equals(Nominal.FIVE_HUNDRED)) {
            value = 500;
        }
    }

    @Override
    public String toString() {
        return "Banknot{" +
                "nominal=" + nominal +
                '}';
    }

    public enum Nominal {
        FIFTY,
        ONE_HUNDRED,
        TWO_HUNDRED,
        FIVE_HUNDRED;
    }
}
