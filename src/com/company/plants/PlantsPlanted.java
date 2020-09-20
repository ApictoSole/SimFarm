package com.company.plants;

public class PlantsPlanted {
    public PlantsValues values;
    public Double amount;   //w hekatarach
    public Double integrity = 100.;
    public Integer age = 0;
    public Boolean ready = false;

    public PlantsPlanted(PlantsValues values, Double amount) {
        this.values = values;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Planted{" +
                ", species=" + values.name +
                ", amount=" + amount +
                ", integrity=" + integrity +
                ", age=" + age +
                ", readyToHarvest=" + ready +
                '}';
    }
}
