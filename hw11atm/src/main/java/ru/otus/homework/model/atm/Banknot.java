package ru.otus.homework.model.atm;

import lombok.Getter;
import lombok.Setter;

public class Banknot {
    @Getter
    @Setter
    private Nominal nominal;

    public Banknot(Nominal nominal) {
        this.nominal = nominal;
    }

    @Override
    public String toString() {
        return "Banknot{" +
                "nominal=" + nominal +
                '}';
    }
}
