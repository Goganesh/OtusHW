package ru.otus.homework.atm;

public enum Nominal {
    FIFTY (50),
    ONE_HUNDRED (100),
    TWO_HUNDRED (200),
    FIVE_HUNDRED (500);

    private final int value;

    Nominal(int value){
        this.value = value;
    }

    public int value(){
        return value;
    }

    @Override
    public String toString() {
        return "Nominal{" +
                "value=" + value +
                '}';
    }
}
