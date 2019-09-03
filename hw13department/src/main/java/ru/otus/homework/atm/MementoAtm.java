package ru.otus.homework.atm;

import lombok.Getter;
import lombok.Setter;
import ru.otus.homework.department.Department;

import java.util.ArrayList;
import java.util.List;

class MementoAtm {
    @Getter
    private List<Casset> cassets;

    MementoAtm(List<Casset> cassets) {
        this.cassets = new ArrayList<>();
        for(Casset casset: cassets){
            this.cassets.add(new CassetImp(casset.nominalType(), casset.getNominalCount()));
        }
    }
}
