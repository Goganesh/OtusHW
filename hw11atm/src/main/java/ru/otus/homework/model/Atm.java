package ru.otus.homework.model;

import lombok.Getter;
import lombok.Setter;
import ru.otus.homework.exception.NoFundsInBalance;
import ru.otus.homework.exception.NotCorrectAmount;
import ru.otus.homework.utils.AtmUtils;
import ru.otus.homework.utils.BanknotUtils;
import ru.otus.homework.utils.CassetUtils;

import java.util.ArrayList;
import java.util.List;

public class Atm {
    @Getter
    @Setter
    private List<Casset> cassets = new ArrayList<>();

    public Atm() {
        cassets.add(new Casset(Nominal.FIFTY));
        cassets.add(new Casset(Nominal.ONE_HUNDRED));
        cassets.add(new Casset(Nominal.TWO_HUNDRED));
        cassets.add(new Casset(Nominal.FIVE_HUNDRED));
    }

}
