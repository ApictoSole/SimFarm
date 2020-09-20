package com.company.plants;

public class PlantsSeeds {
    public PlantsValues values;
    public Double amount;

    public PlantsSeeds(PlantsValues values, Double amount) {
        this.values = values;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "PlantsSeeds{" +
                "values=" + values.name +
                ", amount=" + amount +
                '}';
    }
}
