package com.company.animals;

public class AnimalsReckon {
    public AnimalsValues values;
    public Integer amount = 0;
    public Integer adultAmount = 0;

    public AnimalsReckon(AnimalsValues values, Integer amount, Integer adultAmount) {
        this.values = values;
        this.amount = amount;
        this.adultAmount = adultAmount;
    }

    @Override
    public String toString() {
        return "AnimalsValues{" +
                "values=" + values +
                ", amount=" + amount +
                ", adultAmount=" + adultAmount +
                '}';
    }
}
